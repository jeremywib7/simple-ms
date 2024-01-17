package com.thefuture.accountservice.dezerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thefuture.accountservice.dto.event.BalanceUpdateEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

@Slf4j
public class BalanceUpdateDeserializer implements Deserializer<BalanceUpdateEvent> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public BalanceUpdateEvent deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                System.out.println("Null received at deserializing");
                return null;
            }
            System.out.println("Deserializing...");
            return objectMapper.readValue(new String(data, "UTF-8"), BalanceUpdateEvent.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to BalanceUpdateEvent");
        }
    }

    @Override
    public void close() {
    }
}

