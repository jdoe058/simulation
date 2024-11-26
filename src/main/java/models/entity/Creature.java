package models.entity;

import models.Position;
import utils.PreyFinder;

import java.util.List;

abstract public class Creature extends AliveEntity {

    protected final PreyFinder preyFinder;
    private final int speed;

    public Creature(Position position, PreyFinder preyFinder, int health, int speed) {
        super(position, health);
        this.preyFinder = preyFinder;
        this.speed = speed;
    }

    abstract boolean isValidGoal(Entity entity);

    abstract public void performNearSelf(Entity entity);

    public Position makeMove() {
        List<AliveEntity> preys = preyFinder.findNearPrey(getPosition(), this::isValidGoal);
        if (preys.isEmpty()) {
            var path = preyFinder.findPathPrey(getPosition(), this::isValidGoal);

            if (path.isSteps()) {
                return path.end(speed);
            }

            return getPosition();
        }
        performNearSelf(preys.get(0));
        return getPosition();
    }

}
