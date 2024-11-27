package models.entity;

import models.CreaturePath;
import models.Position;
import utils.PreyFinder;

import java.util.List;

abstract public class Creature extends AliveEntity {

    protected final PreyFinder preyFinder;
    private final int speed;
    private CreaturePathCallback creaturePathCallback;

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
            CreaturePath path = preyFinder.findPathPrey(getPosition(), this::isValidGoal);
            if (creaturePathCallback != null) {
                creaturePathCallback.execute(this, path);
            }

            if (path.isSteps()) {
                return path.end(speed);
            }

            return getPosition();
        }
        performNearSelf(preys.get(0));
        return getPosition();
    }

    public void setCreaturePathCallback(CreaturePathCallback creaturePathCallback) {
        this.creaturePathCallback = creaturePathCallback;
    }

    public interface CreaturePathCallback {
        void execute (Creature creature, CreaturePath path);
    }
}
