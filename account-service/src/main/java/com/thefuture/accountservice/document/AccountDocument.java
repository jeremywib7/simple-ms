package com.thefuture.accountservice.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;

import static com.thefuture.accountservice.service.impl.AccountServiceImpl.generateRandomAccountNumber;

@Document(indexName = "account_document")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDocument extends BaseDocument {
    private String accountNumber = generateRandomAccountNumber();
    private String accountHolder;
    private BigDecimal balance;
}
