package ru.anbn.framework.runner;

public class TestResult {
    private int total;
    private int passed;
    private int failed;

    public void incrementTotal() {
        total++;
    }

    public void incrementPassed() {
        passed++;
    }

    public void incrementFailed() {
        failed++;
    }

    public int getTotal() {
        return total;
    }

    public int getPassed() {
        return passed;
    }

    public int getFailed() {
        return failed;
    }
}
