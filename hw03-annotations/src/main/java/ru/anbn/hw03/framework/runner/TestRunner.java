package ru.anbn.hw03.framework.runner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.anbn.hw03.framework.annotations.After;
import ru.anbn.hw03.framework.annotations.Before;
import ru.anbn.hw03.framework.annotations.Test;
import ru.anbn.hw03.framework.runner.exception.TestInstantiationException;
import ru.anbn.hw03.framework.runner.util.ReflectionUtils;

public class TestRunner {
    private TestRunner() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    private static final Logger log = LoggerFactory.getLogger(TestRunner.class);

    public static void run(Class<?> testClass) {
        log.info("Starting test run for class: {}", testClass.getName());
        TestResult result = new TestResult();
        List<Method> beforeMethods = ReflectionUtils.findMethodWithAnnotation(testClass, Before.class);
        List<Method> testMethods = ReflectionUtils.findMethodWithAnnotation(testClass, Test.class);
        List<Method> afterMethods = ReflectionUtils.findMethodWithAnnotation(testClass, After.class);

        for (Method testMethod : testMethods) {
            result.incrementTotal();
            executeTest(testClass, beforeMethods, testMethod, afterMethods, result);
        }
        printStatistics(result);
    }

    private static void executeTest(
            Class<?> testClass,
            List<Method> beforeMethods,
            Method testMethod,
            List<Method> afterMethods,
            TestResult result) {
        Object testInstance = createTestInstance(testClass);
        try {
            invokeMethods(beforeMethods, testInstance);
            testMethod.invoke(testInstance);
            result.incrementPassed();
        } catch (Exception e) {
            result.incrementFailed();
            log.error("FAILED: {}", testMethod.getName());
        } finally {
            try {
                invokeMethods(afterMethods, testInstance);
            } catch (ReflectiveOperationException e) {
                log.warn("Exception during @After method", e);
            }
        }
    }

    private static Object createTestInstance(Class<?> testClass) {
        try {
            return testClass.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new TestInstantiationException("Cannot create test instance for class: " + testClass.getName(), e);
        }
    }

    private static void invokeMethods(List<Method> methods, Object instance)
            throws IllegalAccessException, InvocationTargetException {
        for (Method method : methods) {
            method.invoke(instance);
        }
    }

    private static void printStatistics(TestResult result) {
        log.info("Test run finished");
        log.info("Total tests: {}", result.getTotal());
        log.info("Passed: {}", result.getPassed());
        log.info("Failed: {}", result.getFailed());
        if (result.getFailed() > 0) {
            log.error("Some tests have failed");
        } else {
            log.info("All tests passed successfully");
        }
    }
}
