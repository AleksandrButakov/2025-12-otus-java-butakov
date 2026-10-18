package ru.anbn.hw12.webserver.web;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class TemplateProcessorImpl {
    private final Configuration configuration;

    public TemplateProcessorImpl() {
        configuration = new Configuration(Configuration.VERSION_2_3_32);
        // Конфигурируем Freemarker искать шаблоны в папке /web/ внутри ресурсов (classpath)
        configuration.setClassForTemplateLoading(TemplateProcessorImpl.class, "/web/");
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    public String getPage(String filename, Map<String, Object> data) {
        try (Writer stream = new StringWriter()) {
            Template template = configuration.getTemplate(filename);
            template.process(data, stream);
            return stream.toString();
        } catch (IOException | freemarker.template.TemplateException e) {
            throw new RuntimeException("Ошибка рендеринга страницы: " + filename, e);
        }
    }
}
