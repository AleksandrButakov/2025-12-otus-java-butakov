package ru.anbu.hw17.multiprocess.server;

import io.grpc.ServerBuilder;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumbersServer {
    private static final Logger log = LoggerFactory.getLogger(NumbersServer.class);
    private static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {
        var server = ServerBuilder.forPort(SERVER_PORT)
                .addService(new NumbersServiceImpl())
                .build();

        server.start();
        log.info("Server waiting for client connections on port {}...", SERVER_PORT);
        server.awaitTermination();
    }
}
