package com.thefuture.service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

import java.util.concurrent.ThreadLocalRandom;

import static com.aventrix.jnanoid.jnanoid.NanoIdUtils.DEFAULT_ALPHABET;
import static com.aventrix.jnanoid.jnanoid.NanoIdUtils.DEFAULT_NUMBER_GENERATOR;

public class GlobalService {

    public static String generateId() {
        return NanoIdUtils.randomNanoId(DEFAULT_NUMBER_GENERATOR, DEFAULT_ALPHABET, 11);
    }


    public static String generateRandomAccountNumber() {
        // Format: <BankCode><BranchCode><AccountNumber>
        String bankCode = "001";  // Replace with a valid bank code
        String branchCode = String.format("%03d", ThreadLocalRandom.current().nextInt(1, 1000)); // 3-digit branch code
        String accountNumber = String.format("%010d", ThreadLocalRandom.current().nextLong(1_000_000_000L)); // 10-digit account number

        return bankCode + branchCode + accountNumber;
    }
}
