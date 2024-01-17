package com.thefuture.transactionservice.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.thefuture.transactionservice.dto.TransactionRequest;
import com.thefuture.transactionservice.dto.TransactionResponse;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest transactionRequest) throws JsonProcessingException;
}
