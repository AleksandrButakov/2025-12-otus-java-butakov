package ru.anbn.dataprocessor;

import java.util.List;
import ru.anbn.model.Measurement;

public interface Loader {

    List<Measurement> load();
}
