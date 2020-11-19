package com.simple.mongo.config;

import com.simple.mongo.ItemId;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class ItemIdCodecProvider implements CodecProvider {
    @SuppressWarnings("unchecked")
    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        if (clazz == ItemId.class) {
            return (Codec<T>) new ItemIdCodec(registry);
        }

        return null;
    }
}
