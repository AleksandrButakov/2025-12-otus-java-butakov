package ru.anbn.handler;

import ru.anbn.listener.Listener;
import ru.anbn.model.Message;

public interface Handler {
    Message handle(Message msg);

    void addListener(Listener listener);

    void removeListener(Listener listener);
}
