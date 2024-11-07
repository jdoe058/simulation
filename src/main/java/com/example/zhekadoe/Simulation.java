package com.example.zhekadoe;

import com.example.zhekadoe.entities.Entity;
import com.example.zhekadoe.entities.Moveable;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

abstract public class Simulation<Image> implements Runnable {
    protected Field field;

    final public String title;
    protected int countIteration;
    final private Random random = new Random();
    final private Consumer<Image> render;

    public Simulation(String title, int width, int height, Consumer<Image> render) {
        this.title = title;
        this.render = render;
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
        e.cell = cell;
        field.put(e, cell);
    };

    abstract public Image getImage();

    abstract List<Runnable> getFirstSpawnActions();

    void init() {
        getFirstSpawnActions().forEach(Runnable::run);
    }

    boolean isOver() {
        // TO-DO добавить сравнение мало травы и много травоядных или мало травоядных и много хищников
        return countIteration++ > 5 || field.getEmptyCellCount() < 10;
    }
    @Override
    public void run() {
        while (!isOver()) {
            var s = getImage();
            field.turnCreature();
            render.accept(s);
        }
    }
}
