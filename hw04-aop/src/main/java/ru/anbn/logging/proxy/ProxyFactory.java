package ru.anbn.logging.proxy;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import ru.anbn.logging.common.Log;
import ru.anbn.logging.common.MethodSignature;
import ru.anbn.logging.common.TestLoggingInterface;
import ru.anbn.logging.target.TestLogging;

public class ProxyFactory {
    private ProxyFactory() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static TestLoggingInterface create() {

        TestLogging target = new TestLogging();

        Set<MethodSignature> methodsToLog = Arrays.stream(target.getClass().getMethods())
                .filter(m -> m.isAnnotationPresent(Log.class))
                .map(MethodSignature::from)
                .collect(Collectors.toSet());

        return (TestLoggingInterface) Proxy.newProxyInstance(
                ProxyFactory.class.getClassLoader(),
                new Class<?>[] {TestLoggingInterface.class},
                new LogInvocationHandler(target, methodsToLog));
    }
}
