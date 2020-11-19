package com.simple.mongo.config;

import com.simple.mongo.Address;
import com.simple.mongo.Person;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class PersonCodecProvider implements CodecProvider {
    private final CodecRegistry codecRegistry;

    public PersonCodecProvider(CodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        if (clazz == Person.class) {
            return (Codec<T>) new PersonCodec(codecRegistry);
        }

        return null;
    }
}
