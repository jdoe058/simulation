package models.entity;

import models.Position;
import utils.PreyFinder;

public class Predator extends Creature {
    private final int attackPower;

    public Predator(Position position, PreyFinder preyFinder, int health, int speed, int attackPower) {
        super(position, preyFinder, health, speed);
        this.attackPower = attackPower;
    }

    @Override
    boolean isValidGoal(Entity entity) {
        return entity instanceof Herbivore && ((Herbivore) entity).isAlive();
    }

    @Override
    public void performNearSelf(Entity entity) {
        ((Herbivore) entity).takeDamage(attackPower);
    }
}
