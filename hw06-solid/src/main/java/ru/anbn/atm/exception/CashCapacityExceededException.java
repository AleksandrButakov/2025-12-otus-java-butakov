package ru.anbn.atm.exception;

public class CashCapacityExceededException extends Exception {
    public CashCapacityExceededException(String message) {
        super(message);
    }
}
