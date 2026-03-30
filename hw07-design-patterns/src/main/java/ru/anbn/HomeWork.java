package ru.anbn;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.anbn.handler.ComplexProcessor;
import ru.anbn.listener.ListenerPrinterConsole;
import ru.anbn.listener.homework.HistoryListener;
import ru.anbn.model.Message;
import ru.anbn.model.ObjectForMessage;
import ru.anbn.processor.*;

public class HomeWork {
    /*
    Реализовать to do:
      1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
      2. Сделать процессор, который поменяет местами значения field11 и field12
      3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
            Секунда должна определяться во время выполнения.
            Тест - важная часть задания
            Обязательно посмотрите пример к паттерну Мементо!
      4. Сделать Listener для ведения истории (подумайте, как сделать, чтобы сообщения не портились)
         Уже есть заготовка - класс HistoryListener, надо сделать его реализацию
         Для него уже есть тест, убедитесь, что тест проходит
    */

    private static final Logger logger = LoggerFactory.getLogger(HomeWork.class);

    public static void main(String[] args) {
        /*
          по аналогии с Demo.class
          из элементов "to do" создать new ComplexProcessor и обработать сообщение
        */

        var processors = List.of(
                new ProcessorConcatFields(),
                new SwapProcessor(),
                new EvenSecondExceptionProcessor(new SystemDateTimeProvider()),
                new LoggerProcessor(new ProcessorUpperField10()));

        // создаём ComplexProcessor с обработкой ошибок
        var complexProcessor = new ComplexProcessor(processors, ex -> logger.error("Error during processing:", ex));

        // listener для печати в консоль
        var listenerPrinter = new ListenerPrinterConsole();
        complexProcessor.addListener(listenerPrinter);

        // listener для истории (Memento)
        var historyListener = new HistoryListener();
        complexProcessor.addListener(historyListener);

        // создаём сообщение с полями 1-13
        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .field11("value11")
                .field12("value12")
                .field13(new ObjectForMessage(List.of("data1", "data2")))
                .build();

        // обрабатываем сообщение
        var result = complexProcessor.handle(message);
        logger.info("Final result: {}", result);

        // проверка истории (пример)
        historyListener.findMessageById(1L).ifPresent(snapshot -> logger.info("Snapshot from history: {}", snapshot));

        // убираем listeners
        complexProcessor.removeListener(listenerPrinter);
        complexProcessor.removeListener(historyListener);
    }
}
