package com.themintiest.core.utils;

import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;
import java.time.Instant;

public class InstantEpochMillisSerializer implements JsonbSerializer<Instant> {
    /**
     * Serializes object into JSON stream.
     *
     * @param obj       Object to serialize.
     * @param generator JSON generator used to write java object to JSON stream.
     * @param ctx
     */
    @Override
    public void serialize(Instant obj, JsonGenerator generator, SerializationContext ctx) {
        generator.write(obj.toEpochMilli());
    }
}
