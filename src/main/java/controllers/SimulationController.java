package controllers;

import models.Field;
import models.entity.MovingCreature;
import models.entity.Grass;
import models.entity.Herbivore;
import view.View;

import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

public class SimulationController implements Runnable {
    private final String title;
    private final Field field;
    private final View<String> consoleView;
    private int turnCount;
    private final AtomicBoolean isPaused = new AtomicBoolean(true);

    public SimulationController(String title,
                                Field field,
                                View<String> consoleView) {
        this.title = title;
        this.field = field;
        this.consoleView = consoleView;
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
        consoleView.add(getTitle());
        turnCount++;
        field.get(MovingCreature.class).forEach(MovingCreature::makeMove);
        consoleView.show();
    }

    public void run() {
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
        consoleView.add(getTitle());
        consoleView.add("Simulation over!");
        consoleView.show();
    }
}
