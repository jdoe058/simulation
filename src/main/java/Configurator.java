public class Configurator {
    public static final int DEFAULT_WIDTH = 10;
    public static final int DEFAULT_HEIGHT = 10;
    public static final int DEFAULT_ROCK_PERCENT = 10;
    public static final int DEFAULT_TREE_PERCENT = 10;
    public static final int DEFAULT_GRASS_PERCENT = 20;
    public static final int DEFAULT_HERBIVORE_PERCENT = 10;
    public static final int DEFAULT_PREDATOR_PERCENT = 3;

    private final EntitySimpleFactory factory;

    public Configurator(EntitySimpleFactory factory) {
        this.factory = factory;
    }

    public void configure () {
        create(factory::createRock, DEFAULT_ROCK_PERCENT);
        create(factory::createTree, DEFAULT_TREE_PERCENT);
        create(factory::createGrass, DEFAULT_GRASS_PERCENT);
        create(factory::createHerbivore, DEFAULT_HERBIVORE_PERCENT);
        create(factory::createPredator, DEFAULT_PREDATOR_PERCENT);
    }

    private void create(Runnable create, int percent) {
        final int size = DEFAULT_WIDTH * DEFAULT_HEIGHT;
        for (int i = 0; i < size * percent / 100; i++) {
            create.run();
        }
    }
}
