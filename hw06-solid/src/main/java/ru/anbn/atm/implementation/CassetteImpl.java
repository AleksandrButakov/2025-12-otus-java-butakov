package ru.anbn.atm.implementation;

import ru.anbn.atm.model.Banknote;
import ru.anbn.atm.model.Cassette;

public class CassetteImpl implements Cassette {
    private final Banknote banknote;
    private int count;

    public CassetteImpl(Banknote banknote, int initialCount) {
        this.banknote = banknote;
        this.count = initialCount;
    }

    @Override
    public void deposit(int count) {
        this.count += count;
    }

    @Override
    public boolean canWithdraw(int count) {
        return this.count >= count;
    }

    @Override
    public void withdraw(int count) {
        this.count -= count;
    }

    @Override
    public int getBalance() {
        return count * banknote.getValue();
    }

    @Override
    public Banknote getBanknote() {
        return banknote;
    }
}
