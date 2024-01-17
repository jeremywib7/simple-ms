package com.thefuture.transactionservice.event;

import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.math.BigDecimal;

@Getter
@Setter
public class BalanceUpdateEvent extends ApplicationEvent {
    private String publicId;
    private BigDecimal newBalance;

    public BalanceUpdateEvent(Object source, String publicId, BigDecimal newBalance) {
        super(source);
        this.publicId = publicId;
        this.newBalance = newBalance;
    }

//    public BalanceUpdateEvent(String publicId, BigDecimal newBalance) {
//        super(orderNumber);
//        this.orderNumber = orderNumber;
//    }
}
