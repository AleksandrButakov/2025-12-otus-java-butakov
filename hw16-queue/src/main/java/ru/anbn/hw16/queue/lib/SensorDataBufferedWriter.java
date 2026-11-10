package ru.anbn.hw16.queue.lib;

import java.util.List;
import ru.anbn.hw16.queue.api.model.SensorData;

public interface SensorDataBufferedWriter {
    void writeBufferedData(List<SensorData> bufferedData);
}
