package com.thefuture.entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;

import static com.thefuture.service.GlobalService.generateRandomAccountNumber;

@Data
@Builder
@Entity(name = "account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity extends BaseEntity {
    private String accountNumber = generateRandomAccountNumber();
    private String accountHolder;
    private BigDecimal balance;
}
