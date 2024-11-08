package com.example.zhekadoe.entities;

import com.example.zhekadoe.Field;

public class Creature extends Entity implements Moveable {
    final protected Field field;
    final protected Class<? extends Entity> targetType;

    public Creature(String image, Field field, Class<? extends Entity> targetType) {
        super(image);
        this.field = field;
        this.targetType = targetType;
    }

    @Override
    public void run() {
        var v = field.getTargetPath(cell, targetType);
        if (v != null) {
            var t = field.get(v).get();
            field.messages.add(this.image + this.cell + " -> " + t.image + t.cell); // TO-DO
            field.waitToRemove.add(this.cell);
            cell = v;
        }
    }
}
