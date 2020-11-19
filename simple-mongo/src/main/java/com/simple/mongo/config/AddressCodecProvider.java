package com.simple.mongo.config;

import com.simple.mongo.Address;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class AddressCodecProvider implements CodecProvider {

    @SuppressWarnings("unchecked")
    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        if (clazz == Address.class) {
            return (Codec<T>) new AddressCodec();
        }

        return null;
    }
}
