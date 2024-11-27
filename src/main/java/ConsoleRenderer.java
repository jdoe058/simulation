import models.Field;
import models.Position;
import models.entity.Entity;
import models.entity.Grass;
import models.entity.Herbivore;
import models.entity.Predator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ConsoleRenderer {
    public static final String DELIMITER = "\t";
    private final Field field;
    private final Collection<String> storage;

    public ConsoleRenderer(Field field, Collection<String> storage) {
        this.field = field;
        this.storage = storage;
    }

    void add(String s) {
        storage.add(s);
    }

    void render() {
        Iterator<String> i = getFieldPicture().iterator();
        Iterator<String> j = storage.iterator();
        System.out.println(i.next() + DELIMITER + getFieldInfoSting().formatted(j.next()));
        j.remove();
        while (i.hasNext() && j.hasNext()) {
            System.out.println(i.next() + DELIMITER + j.next());
            j.remove();
        }

        while (i.hasNext()) {
            System.out.println(i.next());
        }

        while (j.hasNext()) {
            System.out.println(j.next());
            j.remove();
        }
        System.out.println();
    }

    private List<String> getFieldPicture() {
        List<String> list = new ArrayList<>();
        StringBuilder s = new StringBuilder("  ");
        for (int i = 0; i < field.height; i++) {
            s.append("%3d".formatted(i));
        }
        list.add(s.toString());

        for (int i = 0; i < field.height; i++) {
            s = new StringBuilder("%2d".formatted(i));
            for (int j = 0; j < field.width; j++) {
                s.append(field.get(new Position(i, j))
                        .map(Entity::getClass)
                        .map(this::selectEmojiSpriteForEntity)
                        .orElseGet(this::getSpriteForEmptyPosition));
            }
            list.add(s.toString());
        }
        return list;
    }

    private String getFieldInfoSting() {
        return "\t%%s %s%d %s %s %s".formatted(
                getSpriteForEmptyPosition(), field.freeCapacity(),
                getFieldEntityCountString(Grass.class),
                getFieldEntityCountString(Herbivore.class),
                getFieldEntityCountString(Predator.class));
    }

    private String getFieldEntityCountString(Class<? extends Entity> clazz) {
        return "%s%d".formatted(selectEmojiSpriteForEntity(clazz), field.get(clazz).size());
    }

    private String getSpriteForEmptyPosition() {
        return " ..";
    }

    private String selectEmojiSpriteForEntity(Class<? extends Entity> clazz) {
        return switch (clazz.getSimpleName()) {
            case "Rock" -> " *.";
            case "Tree" -> " 🌳";
            case "Grass" -> " 🌾";
            case "Herbivore" -> " 🐷";
            case "Predator" -> " 🐺";
            default -> throw new IllegalStateException("Unexpected value: " + clazz.getSimpleName());
        };
    }
}
