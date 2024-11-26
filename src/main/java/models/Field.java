package models;

import models.entity.Entity;

import java.util.*;

public class Field {
    final public int width;
    final public int height;
    final private Map<Position, Entity> entities = new HashMap<>();

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Optional<Entity> get(Position position) {
        return Optional.ofNullable(entities.get(position));
    }

    public <T extends Entity> List<T> get(Class<T> clazz) {
        List<T> list = new ArrayList<>();

        for (Entity entity : entities.values()) {
            if (clazz.isInstance(entity)) {
                @SuppressWarnings("unchecked")
                T castEntity = (T) entity;
                list.add(castEntity);
            }
        }

        return list;
    }

    public void put(Position position, Entity entity) {
        entities.put(position, entity);
    }

    public void remove(Position position) {
        entities.remove(position);
     }

    public int size() {
        return width * height;
    }

    public int freeCapacity() {
        return size() - entities.size();
    }

    public boolean isValid(Position position) {
        if (position == null) {
            return false;
        }
        boolean isHorizontalValid = 0 <= position.col() && position.col() < width;
        boolean isVerticalValid = 0 <= position.row() && position.row() < height;
        return isHorizontalValid && isVerticalValid;
    }
}
