package ru.anbn.hw16.queue.api;

import java.util.concurrent.TimeUnit;
import ru.anbn.hw16.queue.api.model.SensorData;

public interface SensorsDataChannel {
    boolean push(SensorData sensorData);

    boolean isEmpty();

    SensorData take(long timeout, TimeUnit unit) throws InterruptedException;
}
