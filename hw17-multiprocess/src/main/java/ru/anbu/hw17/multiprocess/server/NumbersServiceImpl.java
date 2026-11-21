package ru.anbu.hw17.multiprocess.server;

import io.grpc.stub.StreamObserver;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.anbu.hw17.multiprocess.protobuf.NumberRequest;
import ru.anbu.hw17.multiprocess.protobuf.NumberResponse;
import ru.anbu.hw17.multiprocess.protobuf.NumbersServiceGrpc;

public class NumbersServiceImpl extends NumbersServiceGrpc.NumbersServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(NumbersServiceImpl.class);

    @Override
    public void generateNumbers(NumberRequest request, StreamObserver<NumberResponse> responseObserver) {
        int currentValue = request.getFirstValue();
        int lastValue = request.getLastValue();

        log.info("Generating numbers from {} to {}", currentValue, lastValue);

        while (currentValue < lastValue) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                log.error("Streaming interrupted", e);
                Thread.currentThread().interrupt();
                break;
            }
            currentValue++;
            NumberResponse response =
                    NumberResponse.newBuilder().setValue(currentValue).build();
            responseObserver.onNext(response);
            log.info("Sent value: {}", currentValue);
        }

        responseObserver.onCompleted();
        log.info("Generation completed");
    }
}
