package ru.anbn.hw13.ioc.services;

import java.util.List;
import ru.anbn.hw13.ioc.model.Equation;

public interface EquationPreparer {
    List<Equation> prepareEquationsFor(int base);
}
