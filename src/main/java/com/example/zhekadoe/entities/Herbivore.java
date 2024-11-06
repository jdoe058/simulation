package com.example.zhekadoe.entities;

import com.example.zhekadoe.Field;

public class Herbivore extends Creature {
    public Herbivore(String image, Field field, Class<? extends Entity> targetType) {
        super(image, field, targetType);
    }
}
