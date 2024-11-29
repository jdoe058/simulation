package models.entity;

import models.Position;

abstract public class AliveEntity extends Entity {
    private Position position;
    private int health;
    private DamageCallback damageCallback;

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

    public void takeDamage(int amount) {
        health -= amount;
        if (damageCallback != null) {
            damageCallback.execute(this, amount);
        }
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setDamageCallback(DamageCallback damageCallback) {
        this.damageCallback = damageCallback;
    }

    public interface DamageCallback {
        void execute(AliveEntity entity, int damage);
    }
}

