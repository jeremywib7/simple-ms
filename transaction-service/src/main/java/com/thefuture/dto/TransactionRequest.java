package com.thefuture.dto;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import static com.aventrix.jnanoid.jnanoid.NanoIdUtils.DEFAULT_ALPHABET;
import static com.aventrix.jnanoid.jnanoid.NanoIdUtils.DEFAULT_NUMBER_GENERATOR;

@Getter
@Setter
public class TransactionRequest {

    public String publicId = NanoIdUtils.randomNanoId(DEFAULT_NUMBER_GENERATOR, DEFAULT_ALPHABET, 11);

    @NotNull
    @Min(value = 10000)
    private BigDecimal nominal;

    @NotNull
    private String senderPublicId;

    @NotNull
    private String receiverPublicId;
}
