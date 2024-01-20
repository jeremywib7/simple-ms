package com.thefuture.controller;

import com.thefuture.dto.AccountResponse;
import com.thefuture.dto.AccountRequest;
import com.thefuture.service.impl.AccountServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/account")
public class AccountController {

    private final AccountServiceImpl accountService;

    @Operation(summary = "Create a bank account")
    @PostMapping("v1")
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountRequest account) {
        return ResponseEntity.ok(accountService.createAccount(account));
    }

    @Operation(summary = "Get a bank account by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the account",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content)})
    @GetMapping("v1")
    public ResponseEntity<AccountResponse> getAccountById(@RequestParam String publicId) {
        return ResponseEntity.ok(accountService.getAccountByPublicId(publicId));
    }
}
