package com.thefuture.event;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionEvent {
    private String senderPublicId;
    private BigDecimal senderNewBalance;
    private String receiverPublicId;
    private BigDecimal receiverNewBalance;
}
