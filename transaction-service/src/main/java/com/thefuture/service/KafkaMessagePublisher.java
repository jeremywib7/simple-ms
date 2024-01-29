package com.thefuture.service;

import com.thefuture.event.TransactionEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaMessagePublisher {

    private final KafkaTemplate<String, Object> template;

    public void sendMessageToTopic(String message) {
        CompletableFuture<SendResult<String, Object>> future = template.send("javatechie-demo2", message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent message={} with offset=[{}]", message, result.getRecordMetadata().offset());
            } else {
                log.error("Unable to send message={} due to: {}", message, ex.getMessage());
            }
        });

    }

    public void sendEventsToTopic(String topic, TransactionEvent transactionEvent) {
        try {
            CompletableFuture<SendResult<String, Object>> future = template.send(topic,
                    transactionEvent);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Sent message={} with offset=[{}]", transactionEvent, result.getRecordMetadata().offset());
                } else {
                    log.error("Unable to send message={} due to: {}", transactionEvent, ex.getMessage());
                }
            });

        } catch (Exception ex) {
            log.error("ERROR={}", ex.getMessage());
        }
    }
}
