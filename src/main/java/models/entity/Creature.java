package models.entity;

import models.CreaturePath;
import models.Field;
import models.Position;
import utils.PreyFinder;

import java.util.List;

abstract public class Creature extends AliveEntity {
    private final Field field;
    protected final PreyFinder preyFinder;
    private final int speed;
    private CreaturePathCallback creaturePathCallback;

    public Creature(Position position, Field field, PreyFinder preyFinder, int health, int speed) {
        super(position, health);
        this.field = field;
        this.preyFinder = preyFinder;
        this.speed = speed;
    }

    abstract boolean isValidGoal(Entity entity);

    abstract public void performNearSelf(List<Position> positions);

    public Position makeMove() {
        List<Position> preys = preyFinder.findNearPrey(getPosition(), this::isValidGoal);
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
        performNearSelf(preys);
        return getPosition();
    }

    public void setCreaturePathCallback(CreaturePathCallback creaturePathCallback) {
        this.creaturePathCallback = creaturePathCallback;
    }

    protected AliveEntity getEntityFromField(Position position) {
        return (AliveEntity) field.get(position).get();
    }

    protected void removeEntityOnField(Position position) {
        field.remove(position);
    }

    public interface CreaturePathCallback {
        void execute(Creature creature, CreaturePath path);
    }
}
