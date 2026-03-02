package ru.anbn.atm.exception;

public class CashCapacityExceededException extends RuntimeException {
    public CashCapacityExceededException(String message) {
        super(message);
    }
}
