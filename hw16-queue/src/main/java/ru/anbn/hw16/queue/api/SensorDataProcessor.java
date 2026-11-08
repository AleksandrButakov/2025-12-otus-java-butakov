package ru.anbn.hw16.queue.api;

import ru.anbn.hw16.queue.api.model.SensorData;

public interface SensorDataProcessor {
    void process(SensorData data);

    default void onProcessingEnd() {}
}
