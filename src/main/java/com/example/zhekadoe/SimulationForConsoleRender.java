package com.example.zhekadoe;

import com.example.zhekadoe.entities.*;

import java.util.List;

public class SimulationForConsoleRender extends Simulation<String> {
    enum SYMBOLS {
        ROCK("\uD83E\uDEA8"),
        TREE("\uD83C\uDF33"),
        GRASS("\uD83C\uDF3D"),
        PREDATOR("\uD83E\uDD81"),
        HERBIVORE("\uD83D\uDC37"),
        DANGER_HERBIVORE("\uD83D\uDC17");

        final public String image;

        SYMBOLS(String image) {
            this.image = image;
        }
    }

    final static private Entity EMPTY_ENTITY = new Entity("..");

    public SimulationForConsoleRender(String title, int width, int height) {
        super(title, width, height);
    }

    @Override
    public String getImage() {
        StringBuilder builder = new StringBuilder("%s, %d\n".formatted(title, countIteration));
        for (int i = 0; i < field.height; i++) {
            for (int j = 0; j < field.width; j++) {
                builder.append(field.get(new Cell(j, i)).orElse(EMPTY_ENTITY).image);
            }
            builder.append('\n');
        }
        return builder.toString();
    }

    @Override
    List<Runnable> getFirstSpawnActions() {
        return List.of(
                new SpawnAction(() -> new Rock(SYMBOLS.ROCK.image), setEntityInRandomEmptyCell, (int) (field.size * .1)),
                new SpawnAction(() -> new Tree(SYMBOLS.TREE.image), setEntityInRandomEmptyCell, (int) (field.size * .1)),
                new SpawnAction(() -> new Grass(SYMBOLS.GRASS.image), setEntityInRandomEmptyCell, (int) (field.size * .18)),
                new SpawnAction(() -> new Predator(SYMBOLS.PREDATOR.image), setEntityInRandomEmptyCell, (int) (field.size * .03)),
                new SpawnAction(() -> new Herbivore(SYMBOLS.HERBIVORE.image), setEntityInRandomEmptyCell, (int) (field.size * .07))
        );
    }

    public static void main(String[] args) {
        List<Simulation<?>> s = List.of(new SimulationForConsoleRender("Simulation One", 20, 10));
        s.get(0).init();
        System.out.println(s.get(0).getImage());
    }
}
