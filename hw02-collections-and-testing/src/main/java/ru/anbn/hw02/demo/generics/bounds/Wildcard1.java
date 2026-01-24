package ru.anbn.hw02.demo.generics.bounds;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.anbn.hw02.demo.generics.bounds.entries.Animal;
import ru.anbn.hw02.demo.generics.bounds.entries.Cat;
import ru.anbn.hw02.demo.generics.bounds.entries.HomeCat;
import ru.anbn.hw02.demo.generics.bounds.entries.WildCat;

public class Wildcard1 {
    private static final Logger logger = LoggerFactory.getLogger(Wildcard1.class);

    public static void main(String[] args) {

        List<Animal> animalList = new ArrayList<>();
        animalList.add(new Animal());

        print(animalList);
        printWild(animalList);

        List<Cat> catList = new ArrayList<>();
        catList.add(new Cat());
        catList.add(new HomeCat("Барсик"));
        catList.add(new WildCat("Багира"));

        // print(catList); //Ошибка
        printWild(catList);
    }

    // Нельзя передать сюда List<Cat>
    private static void print(List<Animal> animalList) {
        animalList.forEach(animal -> logger.info("{}", animal));
        animalList.add(new Animal()); // работает, ошибок нет
    }

    private static void printWild(List<? extends Animal> animalList) {
        animalList.forEach(animal -> logger.info("{}", animal));
        // animalList.add(new Animal()); // не работает, ошибка компиляции
    }
}
