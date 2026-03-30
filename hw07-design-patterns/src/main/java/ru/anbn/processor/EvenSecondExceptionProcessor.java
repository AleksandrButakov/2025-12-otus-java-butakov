package ru.anbn.processor;

import ru.anbn.model.Message;

public class EvenSecondExceptionProcessor implements Processor {

    private final DateTimeProvider dateTimeProvider;

    public EvenSecondExceptionProcessor(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        int second = dateTimeProvider.getDateTime().getSecond();
        if (second % 2 == 0) {
            throw new IllegalArgumentException("Even second exception! Second = " + second);
        }
        return message;
    }
}
