import models.Field;
import models.Position;
import models.entity.AliveEntity;
import models.entity.Creature;
import models.entity.Grass;
import models.entity.Herbivore;

public class Simulation {
    private final Field field;
    private final ConsoleRenderer consoleRenderer;

    public Simulation(
            Field field,
            ConsoleRenderer consoleRenderer) {
        this.field = field;
        this.consoleRenderer = consoleRenderer;
    }

    void run() {
        while (!isOver()) {
            consoleRenderer.render(field);
            turn();
            System.out.println();
        }
    }

    boolean isOver() {
        return field.get(Grass.class).isEmpty() || field.get(Herbivore.class).isEmpty();
    }

    void turn() {
        for (AliveEntity entity : field.get(AliveEntity.class)) {
            if (!entity.isAlive()) {
                field.remove(entity.getPosition());
                continue;
            }

            if (entity instanceof Creature creature) {
                Position start = creature.getPosition();
                Position finish = creature.makeMove();
                if (start != finish) {
                    System.out.printf("%s %s -> %s%n", entity.getClass().getSimpleName(), start, finish);
                    field.remove(start);
                    creature.setPosition(finish);
                    field.put(finish, creature);
                }
            }
        }
    }
}
