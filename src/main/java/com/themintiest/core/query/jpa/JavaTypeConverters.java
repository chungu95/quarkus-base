package com.themintiest.core.query.jpa;

import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class JavaTypeConverters {
    private JavaTypeConverters() {}

    static <T> List<T> convertType(Class<T> javaType, List<String> values) {
        return values.stream().map(value -> convertType(javaType, value)).collect(Collectors.toList());
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @SneakyThrows
    static <T> T convertType(Class<T> javaType, String value) {
        if (javaType.equals(String.class)) {
            return (T) value;
        }
        if (javaType.equals(Integer.class) || javaType.equals(int.class)) {
            return (T) Integer.valueOf(value);
        }
        if (javaType.equals(Long.class) || javaType.equals(long.class)) {
            return (T) Long.valueOf(value);
        }
        if (javaType.equals(BigDecimal.class)) {
            return (T) new BigDecimal(value);
        }
        if (javaType.equals(Instant.class)) {
            return (T) Instant.ofEpochMilli(Long.parseLong(value));
        }
        if (javaType.isEnum()) {
            return (T) Enum.valueOf((Class<Enum>) javaType, value);
        }
        if (javaType.equals(LocalDate.class)) {
            return (T) LocalDate.parse(value);
        }
        if (javaType.equals(LocalDateTime.class)) {
            return (T) LocalDateTime.parse(value);
        }
        if (javaType.equals(UUID.class)) {
            return (T) UUID.fromString(value);
        }
        if (javaType.equals(Boolean.class) || javaType.equals(boolean.class)) {
            return (T) Boolean.valueOf(value);
        }
        Constructor<?> cons = javaType.getConstructor(String.class);
        return (T) cons.newInstance(value);
    }

}
