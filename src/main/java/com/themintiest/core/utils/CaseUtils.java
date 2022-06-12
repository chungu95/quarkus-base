package com.themintiest.core.utils;

public class CaseUtils {
    private static final char NORMAL_CASE_SEPARATOR = ' ';

    private static final char SNAKE_CASE_SEPARATOR = '_';

    private CaseUtils() {}

    public static String snakeToCamel(String snakeCase) {
        StringBuilder sb = new StringBuilder(snakeCase);
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == SNAKE_CASE_SEPARATOR) {
                sb.deleteCharAt(i);
                sb.replace(i, i + 1, String.valueOf(Character.toUpperCase(sb.charAt(i))));
            }
        }
        return sb.toString();
    }

    public static String camelToSnake(String camelCase) {
        return camelToSnake(camelCase, false);
    }

    public static String camelToSnake(String camelCase, boolean upper) {
        return convert(camelCase, SNAKE_CASE_SEPARATOR, upper);
    }

    public static String camelToNormal(String camelCase, boolean upper) {
        return convert(camelCase, NORMAL_CASE_SEPARATOR, upper);
    }

    private static String convert(String str, char separator, boolean upper) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : str.toCharArray()) {
            char nc = upper ? Character.toUpperCase(c) : Character.toLowerCase(c);
            if (Character.isUpperCase(c)) {
                stringBuilder.append(separator).append(nc);
            } else {
                stringBuilder.append(nc);
            }
        }
        return stringBuilder.toString().trim();
    }
}
