package com.thefuture.transactionservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thefuture.transactionservice.document.TransactionDocument;
import com.thefuture.transactionservice.dto.AccountResponse;
import com.thefuture.transactionservice.dto.TransactionRequest;
import com.thefuture.transactionservice.dto.TransactionResponse;
import com.thefuture.transactionservice.event.BalanceUpdateEvent;
import com.thefuture.transactionservice.exception.InsufficientBalanceException;
import com.thefuture.transactionservice.feign.AccountServiceFeign;
import com.thefuture.transactionservice.repository.TransactionRepository;
import com.thefuture.transactionservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final ApplicationEventPublisher applicationEventPublisher;
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
        applicationEventPublisher.publishEvent(new BalanceUpdateEvent(this,
                senderAccount.getPublicId(), senderNewBalance));

        BigDecimal receiverNewBalance = receiverAccount.getBalance().add(transactionRequest.getNominal());
        applicationEventPublisher.publishEvent(new BalanceUpdateEvent(this,
                receiverAccount.getPublicId(), receiverNewBalance));

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
