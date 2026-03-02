package ru.anbn.atm.model;

public interface Cassette {
    void deposit(int count);

    boolean canWithdraw(int count);

    void withdraw(int count);

    int getBalance();

    Banknote getBanknote();
}
