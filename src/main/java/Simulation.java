import models.CreaturePath;
import models.Field;
import models.entity.AliveEntity;
import models.entity.Creature;
import models.entity.Grass;
import models.entity.Herbivore;

import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

public class Simulation implements Runnable {
    private final String title;
    private final Field field;
    private final ConsoleRenderer consoleRenderer;
    private int turnCount;
    private final AtomicBoolean isPaused = new AtomicBoolean(true);

    public Simulation(String title,
                      Field field,
                      ConsoleRenderer consoleRenderer) {
        this.title = title;
        this.field = field;
        this.consoleRenderer = consoleRenderer;
    }

    public boolean isRunning() {
        return !field.get(Grass.class).isEmpty() && !field.get(Herbivore.class).isEmpty();
    }

    public void start() {
        isPaused.set(false);
    }

    public void pause() {
        isPaused.set(true);
    }

    private String getTitle() {
        return "%s(%dx%d) Step: %02d".formatted(title, field.width, field.height, turnCount);
    }

    public void turn() {
        consoleRenderer.add(getTitle());
        turnCount++;
        field.get(Creature.class).forEach(Creature::makeMove);
        consoleRenderer.render();
    }

    public void run() {
        field.get(AliveEntity.class).forEach(this::setEntityTakeDamageCallback);
        field.get(Creature.class).forEach(this::setEntityCreaturePathCallback);
        while (isRunning()) {
            if (!isPaused.get()) {
                turn();
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        consoleRenderer.add(getTitle());
        consoleRenderer.add("Simulation over!");
        consoleRenderer.render();
    }

    private void setEntityTakeDamageCallback(AliveEntity entity) {
        entity.setDamageCallback(this::printTakeDamage);
    }

    private void printTakeDamage(AliveEntity entity, int amount) {
//        String s = "%s%s take %d damage".formatted(entity.getClass().getSimpleName(), entity.getPosition(), amount);
        String s = "%s take %d damage".formatted(entity.getClass().getSimpleName(), amount);
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
