package ru.anbn.processor;

import org.junit.jupiter.api.Test;
import ru.anbn.model.Message;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EvenSecondExceptionProcessorTest {

    @Test
    void shouldThrowExceptionOnEvenSecond() {
        // чётная секунда
        DateTimeProvider provider = new FixedDateTimeProvider(
                LocalDateTime.of(2025, 1, 1, 12, 0, 2));
        EvenSecondExceptionProcessor processor = new EvenSecondExceptionProcessor(provider);

        Message message = new Message.Builder(1).field1("Hello").build();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> processor.process(message));
        assertTrue(ex.getMessage().contains("Even second exception!"));
    }

    @Test
    void shouldReturnMessageOnOddSecond() {
        // нечётная секунда
        DateTimeProvider provider = new FixedDateTimeProvider(
                LocalDateTime.of(2025, 1, 1, 12, 0, 3));
        EvenSecondExceptionProcessor processor = new EvenSecondExceptionProcessor(provider);

        Message message = new Message.Builder(1).field1("Hello").build();
        Message result = processor.process(message);

        assertEquals(message, result);
    }
}
