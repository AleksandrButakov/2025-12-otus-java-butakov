package ru.anbn.hw13.ioc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.io.PrintStream;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Scanner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.anbn.hw13.ioc.appcontainer.AppComponentsContainerImpl;
import ru.anbn.hw13.ioc.appcontainer.api.AppComponent;
import ru.anbn.hw13.ioc.appcontainer.api.AppComponentsContainerConfig;
import ru.anbn.hw13.ioc.config.AppConfig;
import ru.anbn.hw13.ioc.services.*;

class AppTests {
    @DisplayName("Из контекста тремя способами должен корректно доставаться компонент с проставленными полями")
    @ParameterizedTest(name = "Достаем по: {0}")
    @CsvSource(
            value = {
                "GameProcessor, ru.anbn.hw13.ioc.services.GameProcessor",
                "GameProcessorImpl, ru.anbn.hw13.ioc.services.GameProcessor",
                "gameProcessor, ru.anbn.hw13.ioc.services.GameProcessor",
                "IOService, ru.anbn.hw13.ioc.services.IOService",
                "IOServiceStreams, ru.anbn.hw13.ioc.services.IOService",
                "ioService, ru.anbn.hw13.ioc.services.IOService",
                "PlayerService, ru.anbn.hw13.ioc.services.PlayerService",
                "PlayerServiceImpl, ru.anbn.hw13.ioc.services.PlayerService",
                "playerService, ru.anbn.hw13.ioc.services.PlayerService",
                "EquationPreparer, ru.anbn.hw13.ioc.services.EquationPreparer",
                "EquationPreparerImpl, ru.anbn.hw13.ioc.services.EquationPreparer",
                "equationPreparer, ru.anbn.hw13.ioc.services.EquationPreparer"
            })
    void shouldExtractFromContextCorrectComponentWithNotNullFields(String classNameOrBeanId, Class<?> rootClass)
            throws Exception {
        var ctx = new AppComponentsContainerImpl(AppConfig.class);

        assertThat(classNameOrBeanId).isNotEmpty();
        Object component;
        if (classNameOrBeanId.charAt(0) == classNameOrBeanId.toUpperCase().charAt(0)) {
            Class<?> gameProcessorClass = Class.forName("ru.anbn.hw13.ioc.services." + classNameOrBeanId);
            assertThat(rootClass).isAssignableFrom(gameProcessorClass);

            component = ctx.getAppComponent(gameProcessorClass);
        } else {
            component = ctx.getAppComponent(classNameOrBeanId);
        }
        assertThat(component).isNotNull();
        assertThat(rootClass).isAssignableFrom(component.getClass());

        var fields = Arrays.stream(component.getClass().getDeclaredFields())
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .peek(f -> f.setAccessible(true))
                .toList();

        for (var field : fields) {
            var fieldValue = field.get(component);
            assertThat(fieldValue)
                    .isNotNull()
                    .isInstanceOfAny(
                            IOService.class,
                            PlayerService.class,
                            EquationPreparer.class,
                            PrintStream.class,
                            Scanner.class);
        }
    }

    @DisplayName("В контексте не должно быть компонентов с одинаковым именем")
    @Test
    void shouldNotAllowTwoComponentsWithSameName() {
        assertThatCode(() -> new AppComponentsContainerImpl(ConfigWithTwoComponentsWithSameName.class))
                .isInstanceOf(Exception.class);
    }

    @DisplayName(
            "При попытке достать из контекста отсутствующий или дублирующийся компонент, должно выкидываться исключение")
    @Test
    void shouldThrowExceptionWhenContainerContainsMoreThanOneOrNoneExpectedComponents() {
        var ctx = new AppComponentsContainerImpl(ConfigWithTwoSameComponents.class);

        assertThatCode(() -> ctx.getAppComponent(EquationPreparer.class)).isInstanceOf(Exception.class);

        assertThatCode(() -> ctx.getAppComponent(PlayerService.class)).isInstanceOf(Exception.class);

        assertThatCode(() -> ctx.getAppComponent("equationPreparer3")).isInstanceOf(Exception.class);
    }

    @AppComponentsContainerConfig(order = 1)
    public static class ConfigWithTwoComponentsWithSameName {

        @AppComponent(order = 1, name = "equationPreparer")
        public EquationPreparer equationPreparer1() {
            return new EquationPreparerImpl();
        }

        @AppComponent(order = 1, name = "equationPreparer")
        public IOService ioService() {
            return new IOServiceStreams(System.out, System.in);
        }
    }

    @AppComponentsContainerConfig(order = 1)
    public static class ConfigWithTwoSameComponents {

        @AppComponent(order = 1, name = "equationPreparer1")
        public EquationPreparer equationPreparer1() {
            return new EquationPreparerImpl();
        }

        @AppComponent(order = 1, name = "equationPreparer2")
        public EquationPreparer equationPreparer2() {
            return new EquationPreparerImpl();
        }
    }
}
