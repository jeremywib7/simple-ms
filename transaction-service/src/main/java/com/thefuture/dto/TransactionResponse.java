package com.thefuture.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionResponse {
    private String publicId;
    private BigDecimal nominal;
    private String senderPublicId;
    private String receiverPublicId;
}
