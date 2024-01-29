package com.thefuture.consumer;

import com.thefuture.document.AccountDocument;
import com.thefuture.entity.AccountEntity;
import com.thefuture.event.TransactionEvent;
import com.thefuture.repository.AccountDocumentRepository;
import com.thefuture.repository.AccountEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class BalanceConsumer {
    private final AccountDocumentRepository accountDocumentRepository;
    private final ModelMapper modelMapper;
    private final AccountEntityRepository accountEntityRepository;

    @KafkaListener(topics = "${kafka.topics.transaction-listener}", groupId = "com.thefuture")
    @Transactional
    public void handleTransaction(TransactionEvent event) throws AccountNotFoundException {
        String senderPublicId = event.getSenderPublicId();
        String receiverPublicId = event.getReceiverPublicId();

        AccountDocument senderAccDoc = accountDocumentRepository.findByPublicId(senderPublicId);
        senderAccDoc.setBalance(event.getSenderNewBalance());
        AccountDocument receiverAccDoc = accountDocumentRepository.findByPublicId(receiverPublicId);
        receiverAccDoc.setBalance(event.getReceiverNewBalance());

        AccountEntity senderAccEnt = modelMapper.map(senderAccDoc, AccountEntity.class);
        AccountEntity receiverAccEnt = modelMapper.map(receiverAccDoc, AccountEntity.class);

        if (senderAccDoc == null) {
            log.warn(String.format("Sender not found: %s", senderPublicId));
            throw new AccountNotFoundException(String.format("Account not found for public ID: %s", senderPublicId));
        }

        if (receiverAccDoc == null) {
            log.warn(String.format("Receiver not found: %s", receiverPublicId));
            throw new AccountNotFoundException(String.format("Account not found for public ID: %s", receiverPublicId));
        }

        accountDocumentRepository.saveAll(Arrays.asList(senderAccDoc, receiverAccDoc));
        accountEntityRepository.saveAll(Arrays.asList(senderAccEnt, receiverAccEnt));

        log.info(String.format("Sender balance updated: %s", event.getSenderNewBalance()));
        log.info(String.format("Receiver balance updated: %s", event.getReceiverNewBalance()));
    }

}
