package com.themintiest.core.query;

import cz.jirutka.rsql.parser.ast.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagingQuery {
    private String query;
    private Node rsqlQuery;
    private String[] sortBy;
    private Integer page;
    private int size;
}
