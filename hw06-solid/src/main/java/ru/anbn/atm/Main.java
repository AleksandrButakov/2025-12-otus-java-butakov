package ru.anbn.atm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.anbn.atm.core.ATM;
import ru.anbn.atm.exception.InsufficientFundsException;
import ru.anbn.atm.exception.UnsupportedDenominationException;
import ru.anbn.atm.implementation.CassetteImpl;
import ru.anbn.atm.model.Banknote;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ATM atm = new ATM();

        // Initialization of cassettes
        atm.addCassette(new CassetteImpl(Banknote.RUB_50, 10));
        atm.addCassette(new CassetteImpl(Banknote.RUB_5000, 10));
        atm.addCassette(new CassetteImpl(Banknote.RUB_1000, 50));
        atm.addCassette(new CassetteImpl(Banknote.RUB_500, 100));

        log.info("Общий баланс: {}", atm.getFullBalance());

        withdrawCash(atm, 12550);
        withdrawCash(atm, 1550);
        withdrawCash(atm, 1100);

        depositCash(atm, Banknote.RUB_1000, 2);
        depositCash(atm, Banknote.RUB_500, 5);
    }

    private static void withdrawCash(ATM atm, int cash) {
        log.info("Снятие наличных: {}", cash);
        try {
            atm.withdrawCash(cash);
        } catch (InsufficientFundsException e) {
            log.warn("Операция снятия наличных отклонена: {}", e.getMessage());
        }
    }

    private static void depositCash(ATM atm, Banknote banknote, int count) {
        log.info("Внесение наличных: {}={}", banknote, count);
        try {
            atm.depositCash(banknote, count);
        } catch (UnsupportedDenominationException e) {
            log.warn("Операция внесения наличных отклонена: {}", e.getMessage());
        }
    }
}
