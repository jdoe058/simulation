package com.example.zhekadoe.entities;

import com.example.zhekadoe.Field;

public class Predator extends Creature {
    public Predator(String image, Field field, Class<? extends Entity> targetType) {
        super(image, field, targetType);
    }
}
