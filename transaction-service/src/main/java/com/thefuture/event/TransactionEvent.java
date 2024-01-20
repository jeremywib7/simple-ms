package com.thefuture.event;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEvent {
    private String publicId;
    private BigDecimal newBalance;


//    public BalanceUpdateEvent(String publicId, BigDecimal newBalance) {
//        super(orderNumber);
//        this.orderNumber = orderNumber;
//    }
}
