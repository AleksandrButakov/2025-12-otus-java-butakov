package ru.anbu.hw17.multiprocess.client;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.anbu.hw17.multiprocess.protobuf.NumberResponse;

public class ClientStreamObserver implements StreamObserver<NumberResponse> {
    private static final Logger log = LoggerFactory.getLogger(ClientStreamObserver.class);
    private int lastValue = 0;

    @Override
    public void onNext(NumberResponse response) {
        int value = response.getValue();
        log.info("new value:{}", value);
        setLastValue(value);
    }

    @Override
    public void onError(Throwable t) {
        log.error("Error from server", t);
    }

    @Override
    public void onCompleted() {
        log.info("request completed");
    }

    private synchronized void setLastValue(int value) {
        this.lastValue = value;
    }

    public synchronized int getLastValueAndReset() {
        int current = this.lastValue;
        this.lastValue = 0;
        return current;
    }
}
