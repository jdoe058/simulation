package models.entity;

import models.CreaturePath;
import models.Field;
import models.Position;
import utils.PreyFinder;

import java.util.List;

abstract public class Creature extends AliveEntity {
    private Position position;
    private final Field field;
    protected final PreyFinder preyFinder;
    private final int speed;
    private CreaturePathCallback creaturePathCallback;

    public Creature(Position position, Field field, PreyFinder preyFinder, int health, int speed) {
        super(health);
        this.position = position;
        this.field = field;
        this.preyFinder = preyFinder;
        this.speed = speed;
    }

    abstract boolean isValidGoal(Entity entity);

    abstract public void performNearSelf(List<Position> positions);

    public void makeMove() {
        if (!isAlive()) {
            removeEntityOnField(position);
            return;
        }

        List<Position> preys = preyFinder.findNearPrey(position, this::isValidGoal);
        if (preys.isEmpty()) {
            CreaturePath path = preyFinder.findPathPrey(position, this::isValidGoal);
            if (creaturePathCallback != null) {
                creaturePathCallback.execute(this, path);
            }
            if (path.isSteps()) {
                moveSelfOnField(path.end(speed));
            }
        } else {
            performNearSelf(preys);
        }
    }

    public void setCreaturePathCallback(CreaturePathCallback creaturePathCallback) {
        this.creaturePathCallback = creaturePathCallback;
    }

    protected Creature getEntityFromField(Position position) {
        return (Creature) field.get(position).get();
    }

    protected void removeEntityOnField(Position position) {
        field.remove(position);
    }

    private void moveSelfOnField(Position position){
        field.remove(this.position);
        this.position = position;
        field.put(position, this);
    }

    public interface CreaturePathCallback {
        void execute(Creature creature, CreaturePath path);
    }
}
