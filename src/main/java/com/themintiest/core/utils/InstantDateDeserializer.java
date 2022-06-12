package com.themintiest.core.utils;

import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.stream.JsonParser;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class InstantDateDeserializer implements JsonbDeserializer<Instant> {
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(ZoneOffset.UTC);

    /**
     * Deserialize JSON stream into object.
     *
     * @param parser Json parser.
     * @param ctx    Deserialization context.
     * @param rtType Type of returned object.
     * @return Deserialized instance.
     */
    @Override
    public Instant deserialize(JsonParser parser, DeserializationContext ctx, Type rtType) {
        LocalDate localDate = LocalDate.parse(parser.getString(), fmt);
        return localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
    }
}