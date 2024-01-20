package com.thefuture.consumer;

import com.thefuture.document.AccountDocument;
import com.thefuture.event.TransactionEvent;
import com.thefuture.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class BalanceConsumer {
    private final AccountRepository accountRepository;

    @KafkaListener(topics = "transaction-listener", groupId = "com.thefuture")
    public void handleTransaction(TransactionEvent event) throws AccountNotFoundException {
        String senderPublicId = event.getSenderPublicId();
        String receiverPublicId = event.getReceiverPublicId();

        AccountDocument senderAccount = accountRepository.findByPublicId(senderPublicId);
        AccountDocument receiverAccount = accountRepository.findByPublicId(receiverPublicId);

        if (senderAccount == null) {
            log.warn(String.format("Sender not found: %s", senderPublicId));
            throw new AccountNotFoundException(String.format("Account not found for public ID: %s", senderPublicId));
        }

        if (receiverAccount == null) {
            log.warn(String.format("Receiver not found: %s", receiverPublicId));
            throw new AccountNotFoundException(String.format("Account not found for public ID: %s", receiverPublicId));
        }

        senderAccount.setBalance(event.getSenderNewBalance());
        receiverAccount.setBalance(event.getReceiverNewBalance());

        accountRepository.saveAll(Arrays.asList(senderAccount, receiverAccount));

        log.info(String.format("Sender balance updated: %s", event.getSenderNewBalance()));
        log.info(String.format("Receiver balance updated: %s", event.getReceiverNewBalance()));
    }


}
