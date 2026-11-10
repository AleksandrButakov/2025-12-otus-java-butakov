package ru.anbn.hw16.queue.api;

public interface SensorDataProcessingFlow {
    void startProcessing();

    void stopProcessing();

    void bindProcessor(String roomPattern, SensorDataProcessor processor);
}
