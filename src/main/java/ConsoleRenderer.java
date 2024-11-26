import models.Field;
import models.Position;
import models.entity.Entity;

public class ConsoleRenderer {
    void render(Field field) {
        for (int i = 0; i < field.height; i++) {
            for (int j = 0; j < field.width; j++) {
                field.get(new Position(i, j)).ifPresentOrElse(
                        x -> System.out.print(selectUnicodeSpriteForEntity(x)),
                        () -> System.out.print(getSpriteForEmptyPosition()));
            }
            System.out.println();
        }
    }

    private String getSpriteForEmptyPosition() {
        return " ..";
    }

    private String selectUnicodeSpriteForEntity(Entity entity) {
        return switch (entity.getClass().getSimpleName()) {
            case "Rock" -> " *.";
            case "Tree" -> " 🌳";
            case "Grass" -> " 🌾";
            case "Herbivore" -> " 🐷";
            case "Predator" -> " 🐺";
            default -> throw new IllegalStateException("Unexpected value: " + entity.getClass().getSimpleName());
        };
    }
}
