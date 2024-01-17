package com.thefuture.transactionservice.feign;

import com.thefuture.transactionservice.dto.AccountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "account-service", url = "${ACCOUNT_SERVICE_HOST:http://localhost}:8081")
public interface AccountServiceFeign {

    @GetMapping("/api/account/v1")
    AccountResponse getAccountById(@RequestParam String publicId);
}
