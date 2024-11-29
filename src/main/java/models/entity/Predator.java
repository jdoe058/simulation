package models.entity;

import models.Field;
import models.Position;
import utils.PreyFinder;

import java.util.List;

public class Predator extends Creature {
    private final int attackPower;

    public Predator(Position position, Field field, PreyFinder preyFinder, int health, int speed, int attackPower) {
        super(position, field, preyFinder, health, speed);
        this.attackPower = attackPower;
    }

    @Override
    boolean isValidGoal(Entity entity) {
        return entity instanceof Herbivore && ((Herbivore) entity).isAlive();
    }

    @Override
    public void performNearSelf(List<Position> positions) {
        getEntityFromField(positions.get(0)).takeDamage(attackPower);
    }
}
