package ru.anbn.framework.runner.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtils {
    private ReflectionUtils() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static List<Method> findMethodWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
        List<Method> result = new ArrayList<>();

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                result.add(method);
            }
        }
        return result;
    }
}
