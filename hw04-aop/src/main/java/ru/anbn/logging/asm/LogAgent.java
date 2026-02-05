package ru.anbn.logging.asm;

import java.lang.instrument.Instrumentation;

public class LogAgent {
    private LogAgent() {
        throw new UnsupportedOperationException("LogAgent is a utility class and must not be instantiated");
    }

    @SuppressWarnings("java:S1172")
    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new LogClassTransformer());
    }
}
