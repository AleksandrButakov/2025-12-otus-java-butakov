package ru.anbn.listener.homework;

import ru.anbn.listener.Listener;
import ru.anbn.model.Message;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, Message> history = new ConcurrentHashMap<>();

    @Override
    public void onUpdated(Message msg) {
        // Создаём snapshot сообщения через Builder
        // В field13 кладём deep copy объекта
        Message snapshot = msg.toBuilder()
                .field13(msg.getField13() == null ? null : msg.getField13().copy())
                .build();
        history.put(msg.getId(), snapshot);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(history.get(id));
    }
}
