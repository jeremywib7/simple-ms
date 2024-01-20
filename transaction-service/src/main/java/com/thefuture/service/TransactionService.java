package com.thefuture.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.thefuture.dto.TransactionRequest;
import com.thefuture.dto.TransactionResponse;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest transactionRequest) throws JsonProcessingException;
}
