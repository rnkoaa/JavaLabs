package org.amoako.agyei.kafka.serializer;

import org.apache.kafka.common.serialization.Serializer;

public class KafkaProtoSerializer <T extends Event> extends Adapter implements Serializer<T> {
    @Override
    public byte[] serialize(String topic, T data) {
        return new byte[0];
        /**
         * @Override
        public byte[] serialize(final String topic, final Order data) {
        return data.toByteArray();
        }
         */
    }
}
