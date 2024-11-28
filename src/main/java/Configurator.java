import models.Field;
import utils.PreyFinder;

import java.util.ArrayList;

public class Configurator {
    public static final int DEFAULT_WIDTH = 14;
    public static final int DEFAULT_HEIGHT = 15;
    public static final int DEFAULT_ROCK_PERCENT = 15;
    public static final int DEFAULT_TREE_PERCENT = 15;
    public static final int DEFAULT_GRASS_PERCENT = 15;
    public static final int DEFAULT_HERBIVORE_PERCENT = 5;
    public static final int DEFAULT_PREDATOR_PERCENT = 2;

    public Simulation configure () {
        Field field = new Field(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        PreyFinder preyFinder = new PreyFinder(field);
        EntitySimpleFactory factory = new EntitySimpleFactory(field, preyFinder);
        ConsoleRenderer consoleRenderer = new ConsoleRenderer(field, new ArrayList<>());

        create(factory::createRock, DEFAULT_ROCK_PERCENT);
        create(factory::createTree, DEFAULT_TREE_PERCENT);
        create(factory::createGrass, DEFAULT_GRASS_PERCENT);
        create(factory::createHerbivore, DEFAULT_HERBIVORE_PERCENT);
        create(factory::createPredator, DEFAULT_PREDATOR_PERCENT);
        return new Simulation("Simple Word", field, consoleRenderer);
    }

    private void create(Runnable create, int percent) {
        final int size = DEFAULT_WIDTH * DEFAULT_HEIGHT;
        for (int i = 0; i < size * percent / 100; i++) {
            create.run();
        }
    }
}
