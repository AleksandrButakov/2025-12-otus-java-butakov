package ru.anbn.hw02.demo.generics.bounds.entries;

public class HomeCat extends Cat {
    private final String name;

    public HomeCat(String name) {
        this.name = name;
    }

    @Override
    public String getMyau() {
        return "mur-mur-mur";
    }

    @Override
    public String toString() {
        return "HomeCat name:" + name;
    }
}
