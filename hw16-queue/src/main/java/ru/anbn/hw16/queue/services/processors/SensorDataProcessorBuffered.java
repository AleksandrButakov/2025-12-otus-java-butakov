package ru.anbn.hw16.queue.services.processors;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import lombok.extern.slf4j.Slf4j;
import ru.anbn.hw16.queue.api.SensorDataProcessor;
import ru.anbn.hw16.queue.api.model.SensorData;
import ru.anbn.hw16.queue.lib.SensorDataBufferedWriter;

@Slf4j
public class SensorDataProcessorBuffered implements SensorDataProcessor {

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;

    // Используем PriorityBlockingQueue с компаратором по времени измерения
    private final PriorityBlockingQueue<SensorData> dataBuffer;

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
        this.dataBuffer = new PriorityBlockingQueue<>(
                Math.max(bufferSize, 11), Comparator.comparing(SensorData::getMeasurementTime));
    }

    @Override
    public void process(SensorData data) {
        if (data == null) {
            return;
        }

        dataBuffer.put(data);

        // Проверяем размер буфера. Синхронизируемся, чтобы только один поток выполнил flush при переполнении
        if (dataBuffer.size() >= bufferSize) {
            flush();
        }
    }

    public void flush() {
        List<SensorData> bufferedData = new ArrayList<>();

        // Атомарно забираем данные из очереди
        synchronized (dataBuffer) {
            // Если очередь пуста (например, другой поток уже сделал flush), ничего не делаем
            if (dataBuffer.isEmpty()) {
                return;
            }

            /*
            Выгребаем данные. Если это вызвано переполнением, берем строго bufferSize.
            Если это финальный flush() или ручной вызов, забираем всё, что осталось.
            */
            int itemsToDrain = Math.min(dataBuffer.size(), bufferSize);
            for (int i = 0; i < itemsToDrain; i++) {
                SensorData polled = dataBuffer.poll();
                if (polled != null) {
                    bufferedData.add(polled);
                }
            }
        }

        // Запись делаем вне synchronized-блока, чтобы не блокировать другие потоки на время ввода-вывода
        if (!bufferedData.isEmpty()) {
            try {
                writer.writeBufferedData(bufferedData);
            } catch (Exception e) {
                log.error("Ошибка в процессе записи буфера", e);
            }
        }
    }

    @Override
    public void onProcessingEnd() {
        // Забираем всё, что осталось в буфере мелкими или крупными пачками, пока он не опустеет
        while (!dataBuffer.isEmpty()) {
            flush();
        }
    }
}
