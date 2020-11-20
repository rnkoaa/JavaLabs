package com.simple.mongo.config;

import com.simple.mongo.Person;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class PersonCodecProvider implements CodecProvider {

    @SuppressWarnings("unchecked")
    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        if (clazz == Person.class) {
            return (Codec<T>) new PersonCodec(registry);
        }
        return null;
    }
}
