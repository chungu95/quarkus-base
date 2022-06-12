package com.themintiest.core.query.panache;

import com.themintiest.core.query.jpa.JPQLQueryParamsBuilder;
import cz.jirutka.rsql.parser.ast.Node;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import lombok.extern.jbosslog.JBossLog;
import com.themintiest.core.query.PagingQuery;
import com.themintiest.core.query.jpa.JPQLPageQueryVisitor;
import com.themintiest.core.utils.CaseUtils;

import javax.persistence.metamodel.ManagedType;

@JBossLog
public class PanachePageQueryBuilder {
    private final ManagedType<?> managedType;

    private final String entityDisplayName;

    @SuppressWarnings("CdiInjectionPointsInspection")
    public PanachePageQueryBuilder(ManagedType<?> managedType) {
        this.managedType = managedType;
        this.entityDisplayName = CaseUtils.camelToNormal(managedType.getJavaType().getSimpleName(), false);
    }

    public String getEntityDisplayName() {
        return entityDisplayName;
    }

    private Sort parseSortByParam(String[] sortBySpecs) {
        if (sortBySpecs == null) {
            return null;
        }
        Sort sort = Sort.by();
        for (String sortBy : sortBySpecs) {
            if (sortBy.startsWith("-")) {
                sort.and(sortBy.substring(1), Sort.Direction.Descending);
            } else {
                sort.and(sortBy);
            }
        }
        return sort;
    }

    public PanachePagedQuery build(PagingQuery pagingQuery) {
        JPQLQueryParamsBuilder queryParamsBuilder = new JPQLQueryParamsBuilder(managedType);
        Node rsqlQuery = pagingQuery.getRsqlQuery();
        String query = "";
        if (rsqlQuery != null) {
            query = rsqlQuery.accept(JPQLPageQueryVisitor.INSTANCE, queryParamsBuilder);
            log.debug("Paging query: " + query);
        }
        Parameters parameters = new Parameters();
        queryParamsBuilder.getParams().forEach(parameters::and);
        Page page = null;
        if (pagingQuery.getPage() != null) {
            page = new Page(pagingQuery.getPage(), pagingQuery.getSize());
        }
        return PanachePagedQuery.builder()
                .query(query)
                .params(parameters)
                .page(page)
                .sort(parseSortByParam(pagingQuery.getSortBy()))
                .build();
    }
}
