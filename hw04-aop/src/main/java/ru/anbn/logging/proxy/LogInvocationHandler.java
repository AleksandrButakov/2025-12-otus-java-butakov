package ru.anbn.logging.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.anbn.logging.common.MethodSignature;

public class LogInvocationHandler implements InvocationHandler {
    private static final Logger log = LoggerFactory.getLogger(LogInvocationHandler.class);

    private final Object target;
    private final Set<MethodSignature> methodsToLog;

    public LogInvocationHandler(Object target, Set<MethodSignature> methodsToLog) {
        this.target = target;
        this.methodsToLog = methodsToLog;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MethodSignature signature = MethodSignature.from(method);
        if (methodsToLog.contains(signature) && log.isInfoEnabled()) {
            log.info("executed method: {}, params: {}", method.getName(), Arrays.toString(args));
        }
        return method.invoke(target, args);
    }
}
