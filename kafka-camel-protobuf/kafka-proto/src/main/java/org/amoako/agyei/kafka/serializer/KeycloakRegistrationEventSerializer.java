package org.amoako.agyei.kafka.serializer;

import org.amoako.agyei.keycloak.RegistrationEvents.KeycloakRegistrationEvent;
import org.apache.kafka.common.serialization.Serializer;

public class KeycloakRegistrationEventSerializer extends Adapter implements Serializer<KeycloakRegistrationEvent> {

    @Override
    public byte[] serialize(String topic, KeycloakRegistrationEvent data) {
        return data.toByteArray();
    }
}
