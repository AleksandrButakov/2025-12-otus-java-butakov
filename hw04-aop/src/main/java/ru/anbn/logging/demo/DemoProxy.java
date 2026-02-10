package ru.anbn.logging.demo;

import ru.anbn.logging.common.TestLoggingInterface;
import ru.anbn.logging.proxy.ProxyFactory;

public class DemoProxy {
    public static void main(String[] args) {
        TestLoggingInterface logging = ProxyFactory.create();

        logging.calculation(6);
        logging.calculation(3, 4);
        logging.calculation(1, 2, "no log");
    }
}
