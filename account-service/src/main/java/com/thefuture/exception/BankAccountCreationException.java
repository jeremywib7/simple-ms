package com.thefuture.exception;

public class BankAccountCreationException extends RuntimeException {
    public BankAccountCreationException(String message) {
        super(message);
    }

    public BankAccountCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
