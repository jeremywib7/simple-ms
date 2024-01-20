package com.thefuture.service.impl;

import com.thefuture.document.AccountDocument;
import com.thefuture.dto.AccountResponse;
import com.thefuture.dto.AccountRequest;
import com.thefuture.repository.AccountRepository;
import com.thefuture.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public AccountResponse getAccountByPublicId(String publicId) {
        log.info("Fetching AccountDocument for publicId: {}", publicId);
        AccountDocument accountDocument = accountRepository.findByPublicId(publicId);

        if (accountDocument == null) {
            log.warn("AccountDocument not found for publicId: {}", publicId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found for publicId: " + publicId);
        }

        log.info("AccountDocument found: {}", accountDocument);
        return modelMapper.map(accountDocument, AccountResponse.class);
    }

    public AccountResponse createAccount(AccountRequest accountRequest) {
        log.info("Creating account with data: {}", accountRequest);
        AccountDocument accountDocument = modelMapper.map(accountRequest, AccountDocument.class);

        AccountDocument savedAccount = accountRepository.save(accountDocument);
        log.info("Account created and saved: {}", savedAccount.getId());

        return modelMapper.map(savedAccount, AccountResponse.class);
    }

    public static String generateRandomAccountNumber() {
        // Format: <BankCode><BranchCode><AccountNumber>
        String bankCode = "001";  // Replace with a valid bank code
        String branchCode = String.format("%03d", ThreadLocalRandom.current().nextInt(1, 1000)); // 3-digit branch code
        String accountNumber = String.format("%010d", ThreadLocalRandom.current().nextLong(1_000_000_000L)); // 10-digit account number

        return bankCode + branchCode + accountNumber;
    }
}
