package ru.anbn.atm.core;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.anbn.atm.exception.InsufficientFundsException;
import ru.anbn.atm.exception.UnsupportedDenominationException;
import ru.anbn.atm.model.Banknote;
import ru.anbn.atm.model.Cassette;

public class ATM {
    private static final Logger log = LoggerFactory.getLogger(ATM.class);
    private final Map<Banknote, Cassette> cassettes = new TreeMap<>(Comparator.reverseOrder());

    public void addCassette(Cassette cassette) {
        cassettes.put(cassette.getBanknote(), cassette);
    }

    public void depositCash(Banknote banknote, int count) {
        if (!cassettes.containsKey(banknote)) {
            throw new UnsupportedDenominationException("Номинал " + banknote + " не поддерживается");
        }
        cassettes.get(banknote).deposit(count);

        log.info("Внесено: {}={}", banknote, count);
        log.info("Остаток после внесения: {}", getFullBalance());
    }

    public void withdrawCash(int amount) {
        Map<Banknote, Integer> withdrawalPlan = new LinkedHashMap<>();
        int remaining = amount;

        // Check availability of the required banknotes
        for (Cassette cassette : cassettes.values()) {
            int denomValue = cassette.getBanknote().getValue();
            int needed = remaining / denomValue;

            if (needed > 0) {
                // Take the required amount or the available amount from the cassette
                int toTake = Math.min(needed, cassette.getBalance() / denomValue);
                if (toTake > 0) {
                    withdrawalPlan.put(cassette.getBanknote(), toTake);
                    remaining -= toTake * denomValue;
                }
            }
        }

        if (remaining > 0) {
            throw new InsufficientFundsException("Невозможно выдать запрошенную сумму");
        }

        // If the requested sum is assembled, decrease the balance in the cassettes
        withdrawalPlan.forEach((denom, count) -> cassettes.get(denom).withdraw(count));

        log.info("Выдано: {}", withdrawalPlan);
        log.info("Остаток после выдачи: {}", getFullBalance());
    }

    public int getFullBalance() {
        return cassettes.values().stream().mapToInt(Cassette::getBalance).sum();
    }
}
