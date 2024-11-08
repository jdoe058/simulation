package com.example.zhekadoe.entities;

import com.example.zhekadoe.Cell;

public class Entity {
    /**
     * Абстракция изображения, может содержать эмоджи символ, путь к файлу изображения,
     * svg представление, сериализованный объект и др.
     * <p>
     * Note: пока не вижу смысла параметризовать класс, приведет к излишнему усложнению
     * кода, не давая существенных преимуществ
     */
    public String image;
    public Cell cell;

    public Entity(String image) {
        this.image = image;
    }
}
