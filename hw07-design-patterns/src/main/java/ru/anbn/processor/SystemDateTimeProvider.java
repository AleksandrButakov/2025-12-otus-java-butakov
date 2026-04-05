package ru.anbn.processor;

import java.time.LocalDateTime;

public class SystemDateTimeProvider implements DateTimeProvider {
    @Override
    public LocalDateTime getDateTime() {
        return LocalDateTime.now();
    }
}
