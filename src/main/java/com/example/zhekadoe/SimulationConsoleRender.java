package com.example.zhekadoe;

import com.example.zhekadoe.entities.*;

import java.util.List;

public class SimulationConsoleRender extends Simulation {
    enum SYMBOLS {
//        ROCK("\uD83E\uDEA8"),
//        TREE("\uD83C\uDF33"),
//        GRASS("\uD83C\uDF3D"),
//        PREDATOR("\uD83E\uDD81"),
//        HERBIVORE("\uD83D\uDC37");
        //DANGER_HERBIVORE("\uD83D\uDC17");

        ROCK("**"),
        TREE("🌳"),
        GRASS("🌾"),
        PREDATOR("🦁"),
        HERBIVORE("🐖"),
        EMPTY("..");

        final public String image;

        SYMBOLS(String image) {
            this.image = image;
        }
    }

    final static private Entity EMPTY_ENTITY = new Entity(SYMBOLS.EMPTY.image);
    final private String[] image;

    public SimulationConsoleRender(String title, int width, int height) {
        super(title, width, height);
        image = new String[height + 1];
    }

    @Override
    public void prepareImage() {
        for (int i = 0; i < field.height; i++) {
            StringBuilder s = new StringBuilder();
            for (int j = 0; j < field.width; j++) {
                s.append(field.get(new Cell(j, i)).orElse(EMPTY_ENTITY).image);
            }
            image[i] = s.toString();
        }
    }

    @Override
    public void renderImage() {
        while (!field.messages.isEmpty()) {
            for (int i = 0; i < field.height && !field.messages.isEmpty(); i++) {
                image[i] += " ".repeat(4) + field.messages.poll();
            }
        }
        image[field.height] = "%s(%dx%d) %s%d %s%d %s%d %s%d %04d".formatted(
                title, field.width, field.height, "..", field.getEmptyCellCount(),
                SYMBOLS.GRASS.image, field.getEntities(Grass.class).count(),
                SYMBOLS.HERBIVORE.image, field.getEntities(Herbivore.class).count(),
                SYMBOLS.PREDATOR.image, field.getEntities(Predator.class).count(), countIteration);
        System.out.println("\n\033[H\033[2J" + String.join("\n", image) + """
                
                Введите:
                 0 - Старт новой симуляции.
                 1 - Шаг симуляции.
                   - Положительное число для повторения нескольких шагов.
                   - Отрицательное число для повторения шагов симуляции до ее завершения.
                quit для выхода (регистр игнорируется)
                В режиме повторения для паузы и перехода в это меню достаточно нажать Enter.
                >""");
    }

    @Override
    List<Runnable> getFirstSpawnActions() {
        return List.of(
                new SpawnAction(() -> new Rock(SYMBOLS.ROCK.image), setEntityInRandomEmptyCell, (int) (field.size * .1)),
                new SpawnAction(() -> new Tree(SYMBOLS.TREE.image), setEntityInRandomEmptyCell, (int) (field.size * .1)),
                new SpawnAction(() -> new Grass(SYMBOLS.GRASS.image), setEntityInRandomEmptyCell, (int) (field.size * .18)),
                new SpawnAction(() -> new Predator(SYMBOLS.PREDATOR.image, field, Herbivore.class),
                        setEntityInRandomEmptyCell, (int) (field.size * .01)),
                new SpawnAction(() -> new Herbivore(SYMBOLS.HERBIVORE.image, field, Grass.class),
                        setEntityInRandomEmptyCell, (int) (field.size * .07))
        );
    }

    public static void main(String[] args) {
        List<Simulation> simulations = List.of(
                //new SimulationForConsoleRender("Simulation One", 80, 4),
                new SimulationConsoleRender("Simple World", 20, 10)
        );

        for (var s : simulations) {
            s.init();
            s.run();
        }
    }
}
