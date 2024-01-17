package com.thefuture.accountservice.service;

import com.thefuture.accountservice.dto.AccountResponse;

public interface AccountService {
    AccountResponse getAccountByPublicId(String accountId);
}
