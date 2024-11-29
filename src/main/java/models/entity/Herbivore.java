package models.entity;

import models.Field;
import models.Position;
import utils.PreyFinder;

import java.util.List;

public class Herbivore extends MovingCreature {

    public Herbivore(Position position, Field field, PreyFinder preyFinder, int health, int speed) {
        super(position, field, preyFinder, health, speed);
    }

    @Override
    boolean isValidGoal(Entity entity) {
        return entity instanceof Grass;
    }

    @Override
    public void performNearSelf(List<Position> positions) {
        Position position = positions.get(0);
        Entity entity = getEntityFromField(position);
        removeEntityOnField(position);
        if (creatureActionCallback != null) {
            creatureActionCallback.execute("%s%s eats %s%s", this, this.position, entity, position);
        }
    }
}
