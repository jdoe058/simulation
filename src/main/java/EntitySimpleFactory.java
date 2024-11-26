import models.Field;
import models.Position;
import models.entity.*;
import utils.PreyFinder;

import java.util.Random;

public class EntitySimpleFactory {

    private final Field field;
    private final PreyFinder preyFinder;
    private final Random random = new Random();

    public EntitySimpleFactory(Field field, PreyFinder preyFinder) {
        this.field = field;
        this.preyFinder = preyFinder;
    }

    public void createRock() {
        field.put(getRandomFreePosition(), new Rock());
    }

    public void createTree() {
        field.put(getRandomFreePosition(), new Tree());
    }

    public void createGrass() {
        Position position = getRandomFreePosition();
        field.put(position, new Grass(position, 2));
    }

    public void createHerbivore() {
        Position position = getRandomFreePosition();
        field.put(position, new Herbivore(position, preyFinder, 10, 3));
    }

    public void createPredator() {
        Position position = getRandomFreePosition();
        field.put(position, new Predator(position, preyFinder, 10, 5, 6));
    }

    private Position getRandomFreePosition() {
        if (field.freeCapacity() < 1) {
            throw new IndexOutOfBoundsException();
        }
        Position position;
        do {
            position = new Position(random.nextInt(field.height), random.nextInt(field.width));
        } while (field.get(position).isPresent());
        return position;
    }
}
