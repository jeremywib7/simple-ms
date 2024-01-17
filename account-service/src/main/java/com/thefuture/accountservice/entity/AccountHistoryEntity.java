package com.thefuture.accountservice.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String publicId;
    private BigDecimal balance;

    @Column(name = "change_time")
    private LocalDateTime changeTime;
}
