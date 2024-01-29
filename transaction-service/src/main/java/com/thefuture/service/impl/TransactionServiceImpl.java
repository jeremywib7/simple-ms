package com.thefuture.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thefuture.config.properties.KafkaProperties;
import com.thefuture.exception.InsufficientBalanceException;
import com.thefuture.document.TransactionDocument;
import com.thefuture.dto.AccountResponse;
import com.thefuture.dto.TransactionRequest;
import com.thefuture.dto.TransactionResponse;
import com.thefuture.event.TransactionEvent;
import com.thefuture.feign.AccountServiceFeign;
import com.thefuture.repository.TransactionRepository;
import com.thefuture.service.KafkaMessagePublisher;
import com.thefuture.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final KafkaMessagePublisher kafkaMessagePublisher;
    private final KafkaProperties kafkaProperties;
    private final AccountServiceFeign accountService;
    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {
        AccountResponse senderAccount = accountService.getAccountById(transactionRequest.getSenderPublicId());
        AccountResponse receiverAccount = accountService.getAccountById(transactionRequest.getReceiverPublicId());

        checkBalance(senderAccount.getBalance(), transactionRequest.getNominal());

        BigDecimal senderNewBalance = senderAccount.getBalance().subtract(transactionRequest.getNominal());
        BigDecimal receiverNewBalance = receiverAccount.getBalance().add(transactionRequest.getNominal());

        sendTransactionEvent(senderAccount.getPublicId(), senderNewBalance, receiverAccount.getPublicId(), receiverNewBalance);
        saveTransaction(transactionRequest);

        return mapTransactionResponse(transactionRequest);
    }

    private void checkBalance(BigDecimal balance, BigDecimal nominal) {
        if (!isBalanceEnough(balance, nominal)) {
            throw new InsufficientBalanceException("Insufficient balance for the transaction");
        }
    }

    private void sendTransactionEvent(
            String senderPublicId,
            BigDecimal senderNewBalance,
            String receiverPublicId,
            BigDecimal receiverNewBalance) {
        kafkaMessagePublisher.sendEventsToTopic(
                kafkaProperties.getTopics().getTransactionListener(),
                new TransactionEvent(
                        senderPublicId,
                        senderNewBalance,
                        receiverPublicId,
                        receiverNewBalance)
        );
    }

    private void saveTransaction(TransactionRequest transactionRequest) {
        TransactionDocument transactionDocument = modelMapper.map(transactionRequest, TransactionDocument.class);
        transactionRepository.save(transactionDocument);
    }

    private TransactionResponse mapTransactionResponse(TransactionRequest transactionRequest) {
        return modelMapper.map(transactionRequest, TransactionResponse.class);
    }


    public boolean isBalanceEnough(BigDecimal accountBalance, BigDecimal nominal) {
        if (accountBalance.compareTo(nominal) > 0) {
            return true;
        }
        return false;
    }

}
