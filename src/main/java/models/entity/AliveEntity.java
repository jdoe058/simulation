package models.entity;

import models.Position;

abstract public class AliveEntity extends Entity {
    private Position position;
    private int health;

    public AliveEntity(Position position, int health) {
        this.position = position;
        this.health = health;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage() {
        takeDamage(health);
    }

    public void takeDamage(int amount) {
        health -= amount;
        System.out.printf("%s %s -%dhp%n", this.getClass().getSimpleName(), position.toString(), amount);
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}

