package com.thefuture.accountservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountResponse {
    private String publicId;
    private String accountNumber;
    private String accountHolder;
    private BigDecimal balance;
}
