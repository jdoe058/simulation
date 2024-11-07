package com.example.zhekadoe;

import com.example.zhekadoe.entities.*;

import java.util.List;

public class SimulationForConsoleRender extends Simulation<String> {
    enum SYMBOLS {
        ROCK("\uD83E\uDEA8"),
        TREE("\uD83C\uDF33"),
        GRASS("\uD83C\uDF3D"),
        PREDATOR("\uD83E\uDD81"),
        HERBIVORE("\uD83D\uDC37");
        //DANGER_HERBIVORE("\uD83D\uDC17");

        final public String image;

        SYMBOLS(String image) {
            this.image = image;
        }
    }

    final static private Entity EMPTY_ENTITY = new Entity("..");

    public SimulationForConsoleRender(String title, int width, int height) {
        super(title, width, height, System.out::println);
    }

    @Override
    public String getImage() {
        StringBuilder builder = new StringBuilder("\n%s %s%d %s%d %s%d %s%d %04d\n".formatted(
                title,
                "..", field.getEmptyCellCount(),
                SYMBOLS.GRASS.image, field.getEntities(Grass.class).count(),
                SYMBOLS.HERBIVORE.image, field.getEntities(Herbivore.class).count(),
                SYMBOLS.PREDATOR.image, field.getEntities(Predator.class).count(), countIteration));
        for (int i = 0; i < field.height; i++) {


            for (int j = 0; j < field.width; j++) {
                builder.append(field.get(new Cell(j, i)).orElse(EMPTY_ENTITY).image);
            }
            builder
                    .append("\t\t").append(field.messages.poll())
                    .append("\t\t").append(field.messages.poll())
                    .append('\n');

        }
        builder.append(field.messages);
        field.messages.clear();
        return builder.toString();
    }

    @Override
    List<Runnable> getFirstSpawnActions() {
        return List.of(
                new SpawnAction(() -> new Rock(SYMBOLS.ROCK.image), setEntityInRandomEmptyCell, (int) (field.size * .1)),
                new SpawnAction(() -> new Tree(SYMBOLS.TREE.image), setEntityInRandomEmptyCell, (int) (field.size * .1)),
                new SpawnAction(() -> new Grass(SYMBOLS.GRASS.image), setEntityInRandomEmptyCell, (int) (field.size * .18)),
                new SpawnAction(() -> new Predator(SYMBOLS.PREDATOR.image, field, Herbivore.class),
                        setEntityInRandomEmptyCell,  (int) (field.size * .01)),
                new SpawnAction(() -> new Herbivore(SYMBOLS.HERBIVORE.image, field, Grass.class),
                        setEntityInRandomEmptyCell, (int) (field.size * .07))
        );
    }

    public static void main(String[] args) {
        List<Simulation<?>> simulations = List.of(
                //new SimulationForConsoleRender("Simulation One", 80, 4),
                new SimulationForConsoleRender("Simulation Two", 20, 20)
        );

        for(var s: simulations) {
            s.init();
            s.run();
        }
    }
}
