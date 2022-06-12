package com.themintiest.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.BadRequestException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonUtils {

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            Jsonb jsonb = JsonbBuilder.create();
            return jsonb.fromJson(json, clazz);
        } catch (Exception e) {
            throw new BadRequestException("Error when parsing json to object");
        }
    }
}
