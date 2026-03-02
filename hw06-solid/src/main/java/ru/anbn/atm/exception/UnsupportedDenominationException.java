package ru.anbn.atm.exception;

public class UnsupportedDenominationException extends RuntimeException {
    public UnsupportedDenominationException(String message) {
        super(message);
    }
}
