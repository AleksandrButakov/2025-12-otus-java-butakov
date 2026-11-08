package ru.anbn.hw16.queue.api;

import ru.anbn.hw16.queue.api.model.SensorData;

public interface SensorsDataServer {
    void onReceive(SensorData sensorData);
}
