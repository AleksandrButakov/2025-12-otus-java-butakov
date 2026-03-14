package ru.anbn.listener.homework;

import java.util.Optional;
import ru.anbn.listener.Listener;
import ru.anbn.model.Message;

public class HistoryListener implements Listener, HistoryReader {

    @Override
    public void onUpdated(Message msg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        throw new UnsupportedOperationException();
    }
}
