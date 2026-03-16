package ru.anbn.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.List;
import ru.anbn.model.Measurement;

public class ResourcesFileLoader implements Loader {
    private final ObjectMapper mapper = new ObjectMapper();
    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        // читает файл, парсит и возвращает результат
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            return mapper.readValue(inputStream, new TypeReference<List<Measurement>>() {});
        } catch (Exception e) {
            throw new IllegalArgumentException("Resource not found: " + fileName, e);
        }
    }
}
