package com.investing.assistant.holding.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

/** JSON body for PUT /api/holdings/{id}. The ticker can't be changed, so it isn't here. */
public record UpdateHoldingRequest(
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
