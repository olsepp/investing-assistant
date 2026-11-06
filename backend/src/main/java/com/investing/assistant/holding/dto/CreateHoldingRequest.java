package com.investing.assistant.holding.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/** JSON body for POST /api/holdings. The limits match the columns in the database. */
public record CreateHoldingRequest(
        @NotBlank
        @Size(max = 20)
        @Pattern(regexp = "[A-Za-z0-9.\\-]+", message = "may only contain letters, digits, '.' and '-'")
        String ticker,

        @NotNull
        @Positive
        @Digits(integer = 11, fraction = 8)
        BigDecimal quantity,

        @NotNull
        @PositiveOrZero
        @Digits(integer = 13, fraction = 6)
        BigDecimal totalCost,

        @NotNull
        @Pattern(regexp = "[A-Za-z]{3}", message = "must be a 3-letter currency code, e.g. USD")
        String currency) {
}
