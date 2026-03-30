package ru.anbn.listener.homework;

import java.util.Optional;
import ru.anbn.model.Message;

public interface HistoryReader {

    Optional<Message> findMessageById(long id);
}
