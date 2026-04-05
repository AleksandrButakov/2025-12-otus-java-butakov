package ru.anbn.processor;

import java.time.LocalDateTime;

public class FixedDateTimeProvider implements DateTimeProvider {

    private final LocalDateTime fixedDateTime;

    public FixedDateTimeProvider(LocalDateTime fixedDateTime) {
        this.fixedDateTime = fixedDateTime;
    }

    @Override
    public LocalDateTime getDateTime() {
        return fixedDateTime;
    }
}
