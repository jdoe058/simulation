package models.entity;

import models.Position;
import utils.PreyFinder;

public class Herbivore extends Creature {

    public Herbivore(Position position, PreyFinder preyFinder, int health, int speed) {
        super(position, preyFinder, health, speed);
    }

    @Override
    boolean isValidGoal(Entity entity) {
        return entity instanceof Grass && ((Grass) entity).isAlive();
    }

    @Override
    public void performNearSelf(Entity entity) {
        ((Grass) entity).takeDamage();
    }
}
