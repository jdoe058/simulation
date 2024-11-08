package com.example.zhekadoe;

import com.example.zhekadoe.entities.Entity;

import java.util.Random;
import java.util.function.Supplier;

abstract public class Simulation implements Runnable, Renderable {
    protected Field field;

    final public String title;
    protected int countIteration;
    final private Random random = new Random();

    public Simulation(String title, int width, int height) {
        this.title = title;
        field = new Field(width, height);
        countIteration = 0;
    }

    abstract void init();

    private Cell getRandomEmptyCell() {
        if (field.getEmptyCellCount() < 1) {
            throw new IndexOutOfBoundsException();
        }
        Cell cell;
        do {
            cell = new Cell(random.nextInt(field.width), random.nextInt(field.height));
        } while (field.get(cell).isPresent());
        return cell;
    }

    protected void spawn(Supplier<Entity> create, double chance) {
        int count = (int)(field.size * chance);
        while (count-- > 0) {
            var cell = getRandomEmptyCell();
            var e = create.get();
            e.cell = cell;
            field.put(e, cell);
        }
    }

    boolean isOver() {
        // TO-DO добавить сравнение мало травы и много травоядных или мало травоядных и много хищников
        return countIteration++ > 5 || field.getEmptyCellCount() < 10;
    }

    @Override
    public void run() {
        while (!isOver()) {
            prepareImage();
            field.turnCreature();
            renderImage();
        }
    }
}
