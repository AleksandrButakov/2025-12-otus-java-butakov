package ru.anbn.processor;

import ru.anbn.model.Message;

import java.time.LocalDateTime;

public class EvenSecondExceptionProcessor implements Processor {
    @Override
    public Message process(Message message) {
        int second = LocalDateTime.now().getSecond();
        if (second % 2 == 0) {
            throw new IllegalArgumentException("Even second exception! Second = " + second);
        }
        return message;
    }
}
