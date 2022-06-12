package com.themintiest.core.query.jpa;

import lombok.Getter;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.SingularAttribute;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.themintiest.core.query.jpa.JavaTypeConverters.convertType;


public class JPQLQueryParamsBuilder {

    @Getter
    private final Map<String, Object> params = new HashMap<>();

    private final ManagedType<?> managedType;

    @SuppressWarnings("CdiInjectionPointsInspection")
    public JPQLQueryParamsBuilder(ManagedType<?> managedType) {
        this.managedType = managedType;
    }

    public String add(String name, List<String> value) {
        return add(name, (javaType) -> convertType(javaType, value));
    }

    public String add(String name, String value) {
        return add(name, (javaType) -> convertType(javaType, value));
    }

    public String add(String name, Function<Class<?>, Object> converter) {
        Class<?> javaType = getJavaType(name);
        String snakeCaseName = name.replace(".", "_");
        int index = 0;
        String paramName = snakeCaseName;
        while (params.containsKey(paramName)) {
            index++;
            paramName = snakeCaseName + index;
        }
        params.put(paramName, converter.apply(javaType));
        return paramName;
    }

    @SuppressWarnings("rawtypes")
    protected Class<?> getJavaType(String name) {
        ManagedType<?> attributeType = managedType;
        Attribute<?, ?> attribute = null;
        for (String property : name.split("\\.")) {
            attribute = attributeType.getAttribute(property);
            switch (attribute.getPersistentAttributeType()) {
                case EMBEDDED:
                case MANY_TO_ONE:
                case ONE_TO_ONE:
                    attributeType = (ManagedType<?>) ((SingularAttribute) attribute).getType();
                    break;
                default:
                    return attribute.getJavaType();
            }
        }
        return attribute != null ? attribute.getJavaType() : null;
    }
}
