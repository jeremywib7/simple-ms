package com.thefuture.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final KafkaMessagePublisher kafkaMessagePublisher;
    private final AccountServiceFeign accountService;
    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;

    public TransactionResponse createTransaction(TransactionRequest transactionRequest) throws JsonProcessingException {
        AccountResponse senderAccount = accountService.getAccountById(transactionRequest.getSenderPublicId());
        AccountResponse receiverAccount = accountService.getAccountById(transactionRequest.getReceiverPublicId());

        if (!isBalanceEnough(senderAccount.getBalance(), transactionRequest.getNominal())) {
            throw new InsufficientBalanceException("Insufficient balance for the transaction");
        }

        BigDecimal senderNewBalance = senderAccount.getBalance().subtract(transactionRequest.getNominal());
        kafkaMessagePublisher.sendEventsToTopic(new TransactionEvent(senderAccount.getPublicId(), senderNewBalance));

        BigDecimal receiverNewBalance = receiverAccount.getBalance().add(transactionRequest.getNominal());
        kafkaMessagePublisher.sendEventsToTopic(new TransactionEvent(receiverAccount.getPublicId(), receiverNewBalance));

        TransactionDocument transactionDocument = modelMapper.map(transactionRequest, TransactionDocument.class);
        transactionRepository.save(transactionDocument);

        return modelMapper.map(transactionRequest, TransactionResponse.class);
    }

    public boolean isBalanceEnough(BigDecimal accountBalance, BigDecimal nominal) {
        if (accountBalance.compareTo(nominal) > 0) {
            return true;
        }
        return false;
    }

}
