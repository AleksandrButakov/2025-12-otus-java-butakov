package ru.anbn.logging.target;

import ru.anbn.logging.common.Log;
import ru.anbn.logging.common.TestLoggingInterface;

public class TestLogging implements TestLoggingInterface {
    @Log
    @Override
    public void calculation(int param) {
        // business logic
    }

    @Log
    @Override
    public void calculation(int param1, int param2) {
        // business logic
    }

    @Override
    public void calculation(int param1, int param2, String param3) {
        // business logic
    }
}
