package com.themintiest.core.query;

import org.apache.commons.lang3.StringUtils;

public class PagingQueryCombiner {
    private PagingQueryCombiner() {}

    public static PagingQuery or(PagingQuery pagingQuery, String additionalQuery) {
        return buildWrappedPagingQuery(pagingQuery, additionalQuery, ",");
    }

    public static PagingQuery and(PagingQuery pagingQuery, String additionalQuery) {
        return buildWrappedPagingQuery(pagingQuery, additionalQuery, ";");
    }

    private static PagingQuery buildWrappedPagingQuery(PagingQuery pagingQuery, String additionalQuery, String operator) {
        return new WrappedPagingQuery(pagingQuery) {
            @Override
            public String getQuery() {
                if (StringUtils.isNotBlank(super.getQuery())) {
                    return super.getQuery() + operator + additionalQuery;
                }
                return additionalQuery;
            }
        };
    }
}
