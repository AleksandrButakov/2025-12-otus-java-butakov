package ru.anbn.hw16.queue.lib;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import ru.anbn.hw16.queue.api.model.SensorData;

@Slf4j
public class SensorDataBufferedWriterFake implements SensorDataBufferedWriter {

    @Override
    public void writeBufferedData(List<SensorData> bufferedData) {
        var dataToWrite = bufferedData.stream().map(SensorData::toString).collect(Collectors.joining("\n"));
        log.info("Как будто куда-то записываем пачку данных: \n{}", dataToWrite);
    }
}
