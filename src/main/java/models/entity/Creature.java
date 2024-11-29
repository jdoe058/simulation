package models.entity;

abstract public class Creature extends Entity {
    private int health;

    public Creature(int health) {
        this.health = health;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage(int amount) {
        health -= amount;
    }
}

