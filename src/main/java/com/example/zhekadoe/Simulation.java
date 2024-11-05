package com.example.zhekadoe;

import com.example.zhekadoe.entities.Entity;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

abstract public class Simulation<Image> {
    protected Field field;
    final public String title;
    protected int countIteration;
    final private Random random = new Random();

    public Simulation(String title, int width, int height) {
        this.title = title;
        field = new Field(width, height);
        countIteration = 0;
    }

    protected Consumer<Entity> setEntityInRandomEmptyCell = e -> {
        if (field.getEmptyCellCount() < 1) {
            throw new IndexOutOfBoundsException();
        }
        Cell cell;
        do {
            cell = new Cell(random.nextInt(field.width), random.nextInt(field.height));
        } while (field.get(cell).isPresent());
        field.put(cell, e);
    };

    abstract public Image getImage();

    abstract List<Runnable> getFirstSpawnActions();

    void init() {
        getFirstSpawnActions().forEach(Runnable::run);
    }
}
