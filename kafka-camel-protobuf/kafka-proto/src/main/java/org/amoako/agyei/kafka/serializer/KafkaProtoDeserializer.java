package org.amoako.agyei.kafka.serializer;

import org.apache.kafka.common.serialization.Deserializer;

public class KafkaProtoDeserializer<T extends Event> extends Adapter implements Deserializer<T> {
    @Override
    public T deserialize(String topic, byte[] data) {
        return null;
        /**
         *         try {
         return Order.parseFrom(data);
         } catch (final InvalidProtocolBufferException e) {
         LOG.error("Received unparseable message", e);
         throw new RuntimeException("Received unparseable message " + e.getMessage(), e);
         }
         */
    }
}
