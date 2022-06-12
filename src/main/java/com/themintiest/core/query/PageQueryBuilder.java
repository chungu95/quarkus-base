package com.themintiest.core.query;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.RSQLParserException;
import cz.jirutka.rsql.parser.ast.Node;
import lombok.extern.jbosslog.JBossLog;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.BadRequestException;

@JBossLog
public class PageQueryBuilder {

    private static final int MAX_PAGE_SIZE = 50;

    private PageQueryBuilder() {}

    public static PagingQuery of(PageQueryParams pageQueryParams) {
        Node rsqlQuery = null;
        String query = pageQueryParams.getQuery();
        if (StringUtils.isNotBlank(query)) {
            try {
                rsqlQuery = new RSQLParser().parse(query);
            } catch (RSQLParserException e) {
                throw new BadRequestException(e.getCause().getMessage());
            }
        }
        String[] sortBy = parseSortByParam(pageQueryParams.getSortBy());
        if (pageQueryParams.getSize() <= 0) {
            throw new BadRequestException("Page size must be > 0");
        }
        if (pageQueryParams.getSize() > MAX_PAGE_SIZE) {
            log.warn("Page size is too large: " + pageQueryParams.getSize());
            pageQueryParams.setSize(MAX_PAGE_SIZE);
        }
        return PagingQuery.builder()
                .query(query)
                .rsqlQuery(rsqlQuery)
                .sortBy(sortBy)
                .page(pageQueryParams.getPage())
                .size(pageQueryParams.getSize())
                .build();
    }

    private static String[] parseSortByParam(String sortByParam) {
        if (StringUtils.isNotBlank(sortByParam)) {
            return sortByParam.split(",");
        }
        return new String[]{};
    }
}
