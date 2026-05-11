package ru.anbn.hw13.ioc.appcontainer;

import java.lang.reflect.Method;
import java.util.*;
import ru.anbn.hw13.ioc.appcontainer.api.AppComponent;
import ru.anbn.hw13.ioc.appcontainer.api.AppComponentsContainer;
import ru.anbn.hw13.ioc.appcontainer.api.AppComponentsContainerConfig;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?>... configClasses) {
        try {
            sortConfigClasses(configClasses);
            for (Class<?> configClass : configClasses) {
                processConfig(configClass);
            }
        } catch (Exception e) {
            throw new IllegalStateException("Container init failed", e);
        }
    }

    private void processConfig(Class<?> configClass) {
        try {
            validateConfig(configClass);
            Object configInstance = createConfigInstance(configClass);
            Method[] methods = getSortedMethods(configClass);

            for (Method method : methods) {
                if (!isAppComponent(method)) {
                    continue;
                }
                Object bean = createBean(method, configInstance);
                String name = resolveName(method);
                registerBean(bean, name);
            }

        } catch (Exception e) {
            throw new IllegalStateException("Container init failed for " + configClass.getName(), e);
        }
    }

    private void validateConfig(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException("Not config: " + configClass);
        }
    }

    private Object createConfigInstance(Class<?> configClass) {
        try {
            return configClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("Cannot create config instance: " + configClass.getName(), e);
        }
    }

    private void sortConfigClasses(Class<?>[] configClasses) {
        Arrays.sort(configClasses, Comparator.comparingInt(c -> {
            AppComponentsContainerConfig ann = c.getAnnotation(AppComponentsContainerConfig.class);
            return ann == null ? 0 : ann.order();
        }));
    }

    private Method[] getSortedMethods(Class<?> configClass) {
        Method[] methods = configClass.getDeclaredMethods();

        Arrays.sort(methods, Comparator.comparingInt(m -> {
            AppComponent ann = m.getAnnotation(AppComponent.class);
            return ann == null ? 0 : ann.order();
        }));

        return methods;
    }

    private boolean isAppComponent(Method method) {
        return method.isAnnotationPresent(AppComponent.class);
    }

    private String resolveName(Method method) {
        AppComponent ann = method.getAnnotation(AppComponent.class);

        String name = ann.name();
        if (name == null || name.isBlank()) {
            name = method.getName();
        }

        return name;
    }

    private Object createBean(Method method, Object configInstance) {
        try {
            if (method.getParameterCount() == 0) {
                return method.invoke(configInstance);
            }
            Object[] args = resolveArgs(method);
            return method.invoke(configInstance, args);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to create bean: " + method.getName(), e);
        }
    }

    private Object[] resolveArgs(Method method) {
        Class<?>[] params = method.getParameterTypes();
        Object[] args = new Object[params.length];

        for (int i = 0; i < params.length; i++) {
            args[i] = resolveByType(params[i]);
        }
        return args;
    }

    private void registerBean(Object bean, String name) {
        if (appComponentsByName.containsKey(name)) {
            throw new IllegalStateException("Duplicate bean name: " + name);
        }
        appComponents.add(bean);
        appComponentsByName.put(name, bean);
    }

    private Object resolveByType(Class<?> type) {
        List<Object> matches = new ArrayList<>();
        for (Object bean : appComponents) {
            if (bean != null && type.isAssignableFrom(bean.getClass())) {
                matches.add(bean);
            }
        }
        if (matches.isEmpty()) {
            throw new IllegalStateException("No bean for type: " + type);
        }
        if (matches.size() > 1) {
            throw new IllegalStateException("Multiple beans for type: " + type);
        }
        return matches.getFirst();
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return componentClass.cast(resolveByType(componentClass));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <C> C getAppComponent(String componentName) {
        Object bean = appComponentsByName.get(componentName);
        if (bean == null) {
            throw new IllegalStateException("No bean with name: " + componentName);
        }
        return (C) bean;
    }
}
