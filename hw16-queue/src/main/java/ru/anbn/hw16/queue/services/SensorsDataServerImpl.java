package ru.anbn.hw16.queue.services;

import ru.anbn.hw16.queue.api.SensorsDataChannel;
import ru.anbn.hw16.queue.api.SensorsDataServer;
import ru.anbn.hw16.queue.api.model.SensorData;

public class SensorsDataServerImpl implements SensorsDataServer {

    private final SensorsDataChannel sensorsDataChannel;

    public SensorsDataServerImpl(SensorsDataChannel sensorsDataChannel) {
        this.sensorsDataChannel = sensorsDataChannel;
    }

    @Override
    public void onReceive(SensorData sensorData) {
        sensorsDataChannel.push(sensorData);
    }
}
