import models.Field;
import utils.PreyFinder;

import java.util.ArrayList;

public class Main {

    public static final int DEFAULT_WIDTH = 10;
    public static final int DEFAULT_HEIGHT = 10;

    public static void main(String[] args) {
        Field field = new Field(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        PreyFinder preyFinder = new PreyFinder(field);
        EntitySimpleFactory factory = new EntitySimpleFactory(field, preyFinder);
        ConsoleRenderer consoleRenderer = new ConsoleRenderer(field, new ArrayList<>());
        Configurator configurator = new Configurator(factory);
        configurator.configure();

        Simulation simulation = new Simulation("Simple Word", field, consoleRenderer);
        simulation.run();
    }
}
