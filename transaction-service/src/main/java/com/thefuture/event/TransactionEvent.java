package com.thefuture.event;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransactionEvent {
    private String senderPublicId;
    private BigDecimal senderNewBalance;
    private String receiverPublicId;
    private BigDecimal receiverNewBalance;
}
