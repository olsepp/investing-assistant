package com.investing.assistant.holding.dto;

import com.investing.assistant.holding.Holding;

import java.math.BigDecimal;
import java.time.Instant;

/** What the API returns for a holding. Keeps the database entity out of the HTTP layer. */
public record HoldingResponse(
        Long id,
        String ticker,
        BigDecimal quantity,
        BigDecimal totalCost,
        BigDecimal averageBuyPrice,
        String currency,
        Instant createdAt,
        Instant updatedAt) {

    public static HoldingResponse from(Holding holding) {
        return new HoldingResponse(
                holding.getId(),
                holding.getTicker(),
                clean(holding.getQuantity()),
                clean(holding.getTotalCost()),
                clean(holding.getAverageBuyPrice()),
                holding.getCurrency(),
                holding.getCreatedAt(),
                holding.getUpdatedAt());
    }

    /**
     * 10.00000000 -> 10. The database pads numbers to a fixed number of decimals.
     * stripTrailingZeros() alone would turn 100 into 1E+2, so the scale is put back to 0 in that case.
     */
    private static BigDecimal clean(BigDecimal value) {
        BigDecimal stripped = value.stripTrailingZeros();
        return stripped.scale() < 0 ? stripped.setScale(0) : stripped;
    }
}
