package com.example.zhekadoe.entities;

public class Entity {
    /**
     * Абстракция изображения, может содержать эмоджи символ, путь к файлу изображения,
     * svg представление, сериализованный объект и др.
     * <p>
     * Note: пока не вижу смысла параметризовать класс, приведет к излишнему усложнению
     * кода, не давая существенных преимуществ
     */
    public String image;

    public Entity(String image) {
        this.image = image;
    }
}
