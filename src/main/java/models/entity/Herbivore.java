package models.entity;

import models.Field;
import models.Position;
import utils.PreyFinder;

import java.util.List;

public class Herbivore extends Creature {

    public Herbivore(Position position, Field field, PreyFinder preyFinder, int health, int speed) {
        super(position, field, preyFinder, health, speed);
    }

    @Override
    boolean isValidGoal(Entity entity) {
        return entity instanceof Grass;
    }

    @Override
    public void performNearSelf(List<Position> positions) {
        removeEntityOnField(positions.get(0));
    }
}
