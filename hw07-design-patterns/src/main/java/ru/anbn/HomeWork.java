package ru.anbn;

public class HomeWork {
    /*
    Реализовать to do:
      1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
      2. Сделать процессор, который поменяет местами значения field11 и field12
      3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
            Секунда должна определяьться во время выполнения.
            Тест - важная часть задания
            Обязательно посмотрите пример к паттерну Мементо!
      4. Сделать Listener для ведения истории (подумайте, как сделать, чтобы сообщения не портились)
         Уже есть заготовка - класс HistoryListener, надо сделать его реализацию
         Для него уже есть тест, убедитесь, что тест проходит
    */

    public static void main(String[] args) {
        /*
          по аналогии с Demo.class
          из элеменов "to do" создать new ComplexProcessor и обработать сообщение
        */

        /*
        public class Demo {
        private static final Logger logger = LoggerFactory.getLogger(Demo.class);

        public static void main(String[] args) {
            var processors = List.of(new ProcessorConcatFields(), new LoggerProcessor(new ProcessorUpperField10()));

            var complexProcessor = new ComplexProcessor(processors, ex -> {});
            var listenerPrinter = new ListenerPrinterConsole();
            complexProcessor.addListener(listenerPrinter);

            var message = new Message.Builder(1L)
                    .field1("field1")
                    .field2("field2")
                    .field3("field3")
                    .field6("field6")
                    .field10("field10")
                    .build();

            var result = complexProcessor.handle(message);
            logger.info("result:{}", result);

            complexProcessor.removeListener(listenerPrinter);
        }
}
         */



    }
}
