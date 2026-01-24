package ru.anbn.hw02.homework;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {
    private final TreeMap<Customer, String> customers = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        // Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        Map.Entry<Customer, String> entry = customers.firstEntry();
        if (entry == null) {
            return null;
        }
        return new AbstractMap.SimpleEntry<>(
                new Customer(
                        entry.getKey().getId(),
                        entry.getKey().getName(),
                        entry.getKey().getScores()),
                entry.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> entry = customers.higherEntry(customer);
        if (entry == null) {
            return null;
        }
        return new AbstractMap.SimpleEntry<>(
                new Customer(
                        entry.getKey().getId(),
                        entry.getKey().getName(),
                        entry.getKey().getScores()),
                entry.getValue());
    }

    public void add(Customer customer, String data) {
        customers.put(customer, data);
    }
}
