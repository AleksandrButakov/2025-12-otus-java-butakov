package ru.anbn.hw15.executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class NumberSequence {
    private static final Logger logger = LoggerFactory.getLogger(NumberSequence.class);

    private final Object monitor = new Object();
    private int lastThread = 2;

    private void printSequence(int threadId) {
        int currentNumber = 1;
        boolean movingUp = true;

        while (!Thread.currentThread().isInterrupted()) {
            try {
                // Блокируем только момент проверки и печати
                synchronized (monitor) {
                    while (lastThread == threadId) {
                        monitor.wait();
                    }

                    logger.info("Поток {}: {}", threadId, currentNumber);

                    // Изменение счетчика
                    if (movingUp) {
                        if (currentNumber == 10) {
                            movingUp = false;
                            currentNumber = 9;
                        } else {
                            currentNumber++;
                        }
                    } else {
                        if (currentNumber == 1) {
                            movingUp = true;
                            currentNumber = 2;
                        } else {
                            currentNumber--;
                        }
                    }

                    lastThread = threadId;
                    monitor.notifyAll(); // Разбудили соседа
                }

                // sleep вне синхронизированного блока
                TimeUnit.MILLISECONDS.sleep(300);

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public static void main(String[] args) {
        NumberSequence sequence = new NumberSequence();

        Thread t1 = new Thread(() -> sequence.printSequence(1));
        Thread t2 = new Thread(() -> sequence.printSequence(2));

        t1.start();
        t2.start();
    }
}
