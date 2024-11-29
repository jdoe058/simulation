package models.entity;

abstract public class AliveEntity extends Entity {
    private int health;
    private DamageCallback damageCallback;

    public AliveEntity(int health) {
        this.health = health;
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

    public void setDamageCallback(DamageCallback damageCallback) {
        this.damageCallback = damageCallback;
    }

    public interface DamageCallback {
        void execute(AliveEntity entity, int damage);
    }
}

