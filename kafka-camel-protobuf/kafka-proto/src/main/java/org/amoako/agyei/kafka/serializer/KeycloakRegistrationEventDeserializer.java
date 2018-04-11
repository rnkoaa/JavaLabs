package org.amoako.agyei.kafka.serializer;

import com.google.protobuf.InvalidProtocolBufferException;
import org.amoako.agyei.keycloak.RegistrationEvents.KeycloakRegistrationEvent;
import org.apache.kafka.common.serialization.Deserializer;

public class KeycloakRegistrationEventDeserializer extends Adapter implements Deserializer<KeycloakRegistrationEvent> {
    @Override
    public KeycloakRegistrationEvent deserialize(String topic, byte[] data) {
        //return null;
        try {
            return KeycloakRegistrationEvent.parseFrom(data);
        } catch (final InvalidProtocolBufferException e) {
         //   LOG.error("Received unparseable message", e);
            throw new RuntimeException("Received unparseable message " + e.getMessage(), e);
        }
    }
}
