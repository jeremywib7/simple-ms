package com.thefuture.transactionservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thefuture.transactionservice.dto.TransactionRequest;
import com.thefuture.transactionservice.dto.TransactionResponse;
import com.thefuture.transactionservice.service.impl.TransactionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/transaction")
public class TransactionController {
    private final TransactionServiceImpl transactionService;

    @Operation(summary = "Create a New Transaction",
            description = "Endpoint to initiate and process a new financial transaction.")
    @PostMapping("v1")
    public ResponseEntity<TransactionResponse> createTransaction(
            @Valid @RequestBody TransactionRequest transactionRequest) throws JsonProcessingException {
        return ResponseEntity.ok(transactionService.createTransaction(transactionRequest));
    }

}
