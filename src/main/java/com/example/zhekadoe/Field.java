package com.example.zhekadoe;

import com.example.zhekadoe.entities.Entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    public Optional<Entity> get(Cell c) {
        return Optional.ofNullable(fields.get(c));
    }

    public void put(Cell cell, Entity e) {
        fields.put(cell, e);
    }
}
