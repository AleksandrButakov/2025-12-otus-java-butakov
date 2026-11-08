package ru.anbn.hw16.queue.services.processors;

import lombok.extern.slf4j.Slf4j;
import ru.anbn.hw16.queue.api.SensorDataProcessor;
import ru.anbn.hw16.queue.api.model.SensorData;

@Slf4j
public class SensorDataProcessorCommon implements SensorDataProcessor {

    @Override
    public void process(SensorData data) {
        if (data.getValue() == null || data.getValue().isNaN()) {
            return;
        }
        log.info("Обработка данных: {}", data);
    }
}
