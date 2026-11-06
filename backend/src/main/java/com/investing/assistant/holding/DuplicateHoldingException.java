package com.investing.assistant.holding;

/** Thrown when adding a ticker that already has a holding. Turned into HTTP 409 by ApiExceptionHandler. */
public class DuplicateHoldingException extends RuntimeException {

    public DuplicateHoldingException(String ticker) {
        super("A holding for " + ticker + " already exists. Update it instead.");
    }
}
