package ru.anbn.logging.demo;

import ru.anbn.logging.target.TestLogging;

public class DemoAsm {
    public static void main(String[] args) {
        TestLogging obj = new TestLogging();

        obj.calculation(6);
        obj.calculation(3, 4);
        obj.calculation(1, 2, "no log");
    }
}
