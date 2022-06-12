package com.themintiest.core.query;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.RSQLParserException;
import cz.jirutka.rsql.parser.ast.Node;

import javax.ws.rs.BadRequestException;

public class WrappedPagingQuery extends PagingQuery {
    PagingQuery delegate;
    Node cachedRsql;

    public WrappedPagingQuery(PagingQuery delegate) {
        this.delegate = delegate;
    }

    @Override
    public Node getRsqlQuery() {
        if (cachedRsql == null) {
            try {
                cachedRsql = new RSQLParser().parse(getQuery());
            } catch (RSQLParserException e) {
                throw new BadRequestException(e.getCause().getMessage());
            }
        }
        return cachedRsql;
    }

    @Override
    public String getQuery() {
        return delegate.getQuery();
    }

    @Override
    public String[] getSortBy() {
        return delegate.getSortBy();
    }

    @Override
    public Integer getPage() {
        return delegate.getPage();
    }

    @Override
    public int getSize() {
        return delegate.getSize();
    }
}
