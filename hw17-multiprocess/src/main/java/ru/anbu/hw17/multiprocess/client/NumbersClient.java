package ru.anbu.hw17.multiprocess.client;

import io.grpc.ManagedChannelBuilder;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.anbu.hw17.multiprocess.protobuf.NumberRequest;
import ru.anbu.hw17.multiprocess.protobuf.NumbersServiceGrpc;

public class NumbersClient {
    private static final Logger log = LoggerFactory.getLogger(NumbersClient.class);
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws InterruptedException {
        log.info("numbers Client is starting...");

        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        var stub = NumbersServiceGrpc.newStub(channel);
        var observer = new ClientStreamObserver();

        var request =
                NumberRequest.newBuilder().setFirstValue(0).setLastValue(30).build();

        stub.generateNumbers(request, observer);

        int currentValue = 0;
        for (int i = 0; i <= 50; i++) {
            currentValue = currentValue + observer.getLastValueAndReset() + 1;
            log.info("currentValue:{}", currentValue);
            TimeUnit.SECONDS.sleep(1);
        }

        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}
