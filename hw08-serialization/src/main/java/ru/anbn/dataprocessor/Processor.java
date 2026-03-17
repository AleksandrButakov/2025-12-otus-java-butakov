package ru.anbn.dataprocessor;

import java.util.List;
import java.util.Map;
import ru.anbn.model.Measurement;

public interface Processor {

    Map<String, Double> process(List<Measurement> data);
}
