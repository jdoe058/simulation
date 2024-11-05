package com.example.zhekadoe;

import com.example.zhekadoe.entities.Entity;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class SpawnAction implements Runnable {
    Supplier<Entity> createEntity;
    Consumer<Entity> putEntityOnField;
    private final int count;

    public SpawnAction(Supplier<Entity> createEntity, Consumer<Entity> putEntityOnField, int count) {
        this.createEntity = createEntity;
        this.putEntityOnField = putEntityOnField;
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            putEntityOnField.accept(createEntity.get());
        }
    }
}
