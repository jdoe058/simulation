package models.entity;

import models.Field;
import models.Position;
import utils.PreyFinder;

import java.util.List;

public class Predator extends MovingCreature {
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
        Position position = positions.get(0);
        MovingCreature entity = (MovingCreature) getEntityFromField(position);
        entity.takeDamage(attackPower);
        String s = "%s%s attack %s%s";
        if (!entity.isAlive()) {
            removeEntityOnField(position);
            s += " and eats it";
        }
        if (creatureActionCallback != null) {
            creatureActionCallback.execute(s, this, this.position, entity, position);
        }
    }
}
