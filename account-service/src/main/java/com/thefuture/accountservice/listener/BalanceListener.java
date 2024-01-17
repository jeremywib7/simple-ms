package com.thefuture.accountservice.listener;

import com.thefuture.accountservice.document.AccountDocument;
import com.thefuture.accountservice.dto.event.BalanceUpdateEvent;
import com.thefuture.accountservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class BalanceListener {
    private final AccountRepository accountRepository;

//    @KafkaListener(topics = "balance-update-topic")
    public void handleBalanceUpdateEvent(BalanceUpdateEvent event) {
        AccountDocument accountDocument = accountRepository.findByPublicId(event.getPublicId());
        if (accountDocument == null) {
            log.warn("Account not found:" + event.getPublicId());
            throw new RuntimeException("Account not found:" + event.getPublicId());
        }
        accountDocument.setBalance(event.getNewBalance());
        accountRepository.save(accountDocument);
        log.warn("Account balance updated:" + event.getPublicId());
    }
}
