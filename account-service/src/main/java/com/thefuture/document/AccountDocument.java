package com.thefuture.document;

import com.thefuture.service.impl.AccountServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;

@Document(indexName = "account_document")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDocument extends BaseDocument {
    private String accountNumber = AccountServiceImpl.generateRandomAccountNumber();
    private String accountHolder;
    private BigDecimal balance;
}
