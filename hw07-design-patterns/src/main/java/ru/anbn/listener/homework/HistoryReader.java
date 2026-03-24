package ru.anbn.listener.homework;

import ru.anbn.model.Message;

import java.util.Optional;

public interface HistoryReader {

    Optional<Message> findMessageById(long id);
}
