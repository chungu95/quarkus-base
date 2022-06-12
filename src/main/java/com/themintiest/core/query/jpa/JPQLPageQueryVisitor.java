package com.themintiest.core.query.jpa;

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import cz.jirutka.rsql.parser.ast.LogicalNode;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.OrNode;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import com.themintiest.core.utils.CaseUtils;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import static cz.jirutka.rsql.parser.ast.RSQLOperators.EQUAL;
import static cz.jirutka.rsql.parser.ast.RSQLOperators.GREATER_THAN;
import static cz.jirutka.rsql.parser.ast.RSQLOperators.GREATER_THAN_OR_EQUAL;
import static cz.jirutka.rsql.parser.ast.RSQLOperators.IN;
import static cz.jirutka.rsql.parser.ast.RSQLOperators.LESS_THAN;
import static cz.jirutka.rsql.parser.ast.RSQLOperators.LESS_THAN_OR_EQUAL;
import static cz.jirutka.rsql.parser.ast.RSQLOperators.NOT_EQUAL;
import static cz.jirutka.rsql.parser.ast.RSQLOperators.NOT_IN;

public class JPQLPageQueryVisitor implements RSQLVisitor<String, JPQLQueryParamsBuilder> {

    public static final JPQLPageQueryVisitor INSTANCE = new JPQLPageQueryVisitor();

    private static final Map<ComparisonOperator, String> JPQL_OPERATOR_MAPPINGS = Map.of(
        EQUAL, "=",
        NOT_EQUAL, "<>",
        GREATER_THAN, ">",
        GREATER_THAN_OR_EQUAL, ">=",
        LESS_THAN, "<",
        LESS_THAN_OR_EQUAL, "<=",
        IN, "in",
        NOT_IN, "not in"
    );

    @Override
    public String visit(AndNode node, JPQLQueryParamsBuilder param) {
        return visit(node, " and ", param);
    }

    @Override
    public String visit(OrNode node, JPQLQueryParamsBuilder param) {
        return visit(node, " or ", param);
    }

    @Override
    public String visit(ComparisonNode node, JPQLQueryParamsBuilder param) {
        String name = CaseUtils.snakeToCamel(node.getSelector());
        ComparisonOperator op = node.getOperator();
        String operator = JPQL_OPERATOR_MAPPINGS.get(op);
        List<String> args = node.getArguments();
        String paramName;
        if (op.isMultiValue()) {
            paramName = param.add(name, args);
        } else {
            String arg = args.get(0);
            if (op.equals(EQUAL) || op.equals(NOT_EQUAL)) {
                if (arg.startsWith("*") || arg.endsWith("*")) {
                    arg = arg.replace("*", "%");
                    operator = op.equals(EQUAL) ? "like" : "not like";
                    paramName = param.add(name, arg);
                    return String.format("(LOWER(%s) %s LOWER(:%s))", name, operator, paramName);
                }
            }
            paramName = param.add(name, arg);
        }

        return String.format("(%s %s :%s)", name, operator, paramName);
    }

    private String visit(LogicalNode node, String delimiter, JPQLQueryParamsBuilder param) {
        StringJoiner joiner = new StringJoiner(delimiter, "(", ")");
        for (Node child : node) {
            String fragment = child.accept(this, param);
            joiner.add(fragment);
        }
        return joiner.toString();
    }
}
