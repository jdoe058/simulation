package utils;

import models.entity.*;
import org.jetbrains.annotations.NotNull;
import models.*;

import java.util.*;

public class PreyFinder {
    final private Field field;

    public PreyFinder(Field field) {
        this.field = field;
    }

    public CreaturePath findPathPrey(Position position, Validator validator) {
        CreaturePath start = new CreaturePath(position);

        Set<Position> burningPositions = new HashSet<>();
        Queue<CreaturePath> paths = new ArrayDeque<>();

        burningPositions.add(position);
        paths.add(start);

        while (!paths.isEmpty()) {
            CreaturePath path = paths.poll();
            if (!findNearPrey(path.end(), validator).isEmpty()) {
                return path;
            }
            List<Position> freePositions = findNearFreePositions(path.end());
            freePositions.removeAll(burningPositions);

            for (Position p : freePositions) {
                burningPositions.add(p);
                paths.add(new CreaturePath(path, p));
            }
        }
        return start;
    }

    public List<AliveEntity> findNearPrey(Position position, Validator validator) {
        List<AliveEntity> list = new ArrayList<>();
        for (Position p : findNearAllPositions(position)) {
            Optional<Entity> entity = field.get(p);
            if (entity.isPresent()) {
                if (validator.isValid(entity.get())) {
                    list.add((AliveEntity) entity.get());
                }
            }
        }
        return list;
    }

    public interface Validator {
        boolean isValid(Entity entity);
    }

    private @NotNull List<Position> findNearFreePositions(Position position) {
        List<Position> list = new ArrayList<>();
        for (Position p : findNearAllPositions(position)) {
            if (field.get(p).isEmpty()) {
                list.add(p);
            }
        }
        return list;
    }

    private @NotNull List<Position> findNearAllPositions(Position position) {
        List<Position> list = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                Position nearPosition = position.add(i, j);
                if (field.isValid(nearPosition)) {
                    list.add(nearPosition);
                }
            }
        }
        return list;
    }
}
