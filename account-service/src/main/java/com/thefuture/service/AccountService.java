package com.thefuture.service;

import com.thefuture.dto.AccountResponse;

public interface AccountService {
    AccountResponse getAccountByPublicId(String accountId);
}
