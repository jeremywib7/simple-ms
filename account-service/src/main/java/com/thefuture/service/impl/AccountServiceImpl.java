package com.thefuture.service.impl;

import com.thefuture.document.AccountDocument;
import com.thefuture.dto.AccountRequest;
import com.thefuture.dto.AccountResponse;
import com.thefuture.entity.AccountEntity;
import com.thefuture.exception.BankAccountCreationException;
import com.thefuture.repository.AccountDocumentRepository;
import com.thefuture.repository.AccountEntityRepository;
import com.thefuture.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountDocumentRepository accountDocumentRepository;
    private final AccountEntityRepository accountEntityRepository;
    private final ModelMapper modelMapper;

    public AccountResponse getAccountByPublicId(String publicId) {
        log.info("Fetching AccountDocument for publicId: {}", publicId);
        AccountEntity accountEntity = accountEntityRepository.findByPublicId(publicId);

        if (accountEntity == null) {
            log.warn("AccountDocument not found for publicId: {}", publicId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found for publicId: " + publicId);
        }

        log.info("AccountDocument found: {}", accountEntity);
        return modelMapper.map(accountEntity, AccountResponse.class);
    }

    public AccountResponse createAccount(AccountRequest accountRequest) {
        log.info("Creating account with data: {}", accountRequest);
        AccountDocument savedAccount = createBankAccount(accountRequest);
        log.info("Account created and saved: {}", savedAccount.getId());
        return modelMapper.map(savedAccount, AccountResponse.class);
    }

    @Transactional
    public AccountDocument createBankAccount(AccountRequest accountRequest) {
        try {
            AccountEntity accountEntity = modelMapper.map(accountRequest, AccountEntity.class);
            var result = accountEntityRepository.save(accountEntity);

            AccountDocument accountDocument = modelMapper.map(accountRequest, AccountDocument.class);
            accountDocument.setPublicId(result.getPublicId());
            accountDocument.setAccountNumber(result.getAccountNumber());
            accountDocumentRepository.save(accountDocument);

            return accountDocument;
        } catch (Exception e) {
            log.error("Error creating bank account", e);
            throw new BankAccountCreationException("Error creating bank account", e);
        }

    }

}
