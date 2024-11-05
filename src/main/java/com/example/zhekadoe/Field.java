package com.example.zhekadoe;

import com.example.zhekadoe.entities.Entity;

import java.util.*;

/**
 * Представляет поле симуляции, является набором ячеек с координатами от левого верхнего угла
 */
public class Field {
    final public int width;
    final public int height;
    final public int size;
    final private Map<Cell, Entity> fields = new HashMap<>();

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        size = width * height;
    }

    public int getEmptyCellCount() {
        return size - fields.size();
    }

    Set<Cell> getNeighboringCells(Cell c) {
        HashSet<Cell> cells = new HashSet<>();
        for (int i = Math.max(0, c.y() - 1); i < Math.min(height, c.y() + 2); i++) {
            for (int j = Math.max(0, c.x() - 1); j < Math.min(width, c.x() + 2); j++) {
                if (i != c.y() || j != c.x()) {
                    cells.add(new Cell(j, i));
                }
            }
        }
        return cells;
    }

    public Optional<Entity> get(Cell c) {
        return Optional.ofNullable(fields.get(c));
    }

    public void put(Cell cell, Entity e) {
        fields.put(cell, e);
    }
}
