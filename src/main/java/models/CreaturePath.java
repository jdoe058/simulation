package models;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Deque;

public class CreaturePath {
    final private Deque<Position> path;

    public CreaturePath(Position position) {
        path = new ArrayDeque<>();
        add(position);
    }

    public CreaturePath(@NotNull CreaturePath creaturePath, Position position) {
        path = new ArrayDeque<>(creaturePath.path);
        add(position);
    }

    public void add(Position position) {
        path.add(position);
    }

    public Position end() {
        return path.peekLast();
    }

    public Position end(int numberOfSteps) {
        if (path.size() < numberOfSteps) {
            return end();
        }
        Position position = path.poll();
        for (int i = 1; i < numberOfSteps; i++) {
            position = path.poll();
        }
        return position;

    }

    public boolean isSteps() {
        return path.size() > 1;
    }
}
