package com.investing.assistant.holding;

/** Thrown when no holding has the requested id. Turned into HTTP 404 by ApiExceptionHandler. */
public class HoldingNotFoundException extends RuntimeException {

    public HoldingNotFoundException(Long id) {
        super("Holding " + id + " not found");
    }
}
