import models.CreaturePath;
import models.Field;
import models.Position;
import models.entity.AliveEntity;
import models.entity.Creature;
import models.entity.Grass;
import models.entity.Herbivore;

public class Simulation {
    private final String title;
    private final Field field;
    private final ConsoleRenderer consoleRenderer;
    private int turnCount;

    public Simulation(String title,
            Field field,
            ConsoleRenderer consoleRenderer) {
        this.title = title;
        this.field = field;
        this.consoleRenderer = consoleRenderer;
    }

    void run() {
        field.get(AliveEntity.class).forEach(this::setEntityTakeDamageCallback);
        field.get(Creature.class).forEach(this::setEntityCreaturePathCallback);
        while (!isOver()) {
            consoleRenderer.add("%s: %d".formatted(title, turnCount++));
            turn();
            consoleRenderer.render();
        }
    }

    boolean isOver() {
        return field.get(Grass.class).isEmpty() || field.get(Herbivore.class).isEmpty();
    }

    void turn() {
        field.get(Creature.class).forEach(this::moveCreature);
        field.get(AliveEntity.class).forEach(this::removeCorpse);
    }

    void moveCreature(Creature creature) {
        if (!creature.isAlive()) {
            return;
        }
        Position start = creature.getPosition();
        Position finish = creature.makeMove();
        if (start != finish) {
            field.remove(start);
            creature.setPosition(finish);
            field.put(finish, creature);
        }
    }

    void removeCorpse(AliveEntity entity) {
        if (!entity.isAlive()) {
            field.remove(entity.getPosition());
        }
    }

    private void setEntityTakeDamageCallback(AliveEntity entity) {
        entity.setDamageCallback(this::printTakeDamage);
    }

    private void printTakeDamage(AliveEntity entity, int amount) {
        String s = "%s%s take %d damage".formatted(entity.getClass().getSimpleName(), entity.getPosition(), amount);
        if (!entity.isAlive()) {
            s += " and R.I.P";
        }
        consoleRenderer.add(s);
    }

    private void setEntityCreaturePathCallback(Creature creature) {
        creature.setCreaturePathCallback(this::printCreaturePath);
    }

    private void printCreaturePath(Creature creature, CreaturePath path) {
        if (path.isSteps()) {
            String s = "%s finds prey along the way %s".formatted(
                    creature.getClass().getSimpleName(),
                    path.get());
            consoleRenderer.add(s);
        }
    }
}
