package ru.anbn.logging.common;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public final class MethodSignature {
    private final String name;
    private final Class<?>[] parameterTypes;

    public MethodSignature(String name, Class<?>[] parameterTypes) {
        this.name = name;
        this.parameterTypes = parameterTypes;
    }

    public static MethodSignature from(Method method) {
        return new MethodSignature(method.getName(), method.getParameterTypes());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MethodSignature that)) return false;
        return Objects.equals(name, that.name) && Arrays.equals(parameterTypes, that.parameterTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, Arrays.hashCode(parameterTypes));
    }
}
