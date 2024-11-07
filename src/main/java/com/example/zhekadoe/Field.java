package com.example.zhekadoe;

import com.example.zhekadoe.entities.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * Представляет поле симуляции,
 * является набором ячеек с координатами от левого верхнего угла
 */
public class Field {
    final public int width;
    final public int height;
    final public int size;
    final private Map<Cell, Entity> entities = new HashMap<>();
    final public Queue<String> messages = new ArrayDeque<>();
    final public List<Cell> waitToRemove = new ArrayList<>();

    /**
     * Возвращает множество соседних ячеек, содержащих объекты целевого типа,
     * или свободные ячейки если целевой тип не указан
     *
     * @param cell       целевая ячейка
     * @param targetType целевой тип существ или *null* для свободных соседей
     * @return множество соседних ячеек
     */
    private Set<Cell> getNeighbours(Cell cell, Class<? extends Entity> targetType) {
        HashSet<Cell> cells = new HashSet<>();
        for (int i = Math.max(0, cell.y() - 1); i < Math.min(height, cell.y() + 2); i++) {
            for (int j = Math.max(0, cell.x() - 1); j < Math.min(width, cell.x() + 2); j++) {
                if (i != cell.y() || j != cell.x()) {
                    Cell c = new Cell(j, i);
                    if (targetType == null) {
                        if (!entities.containsKey(c)) {
                            cells.add(c);
                        }
                    } else if (targetType.isInstance(entities.get(c))) {
                        cells.add(c);
                    }
                }
            }
        }
        return cells;
    }

    /**
     * Реализует поиск цели
     * <p>
     * Использует <a href="https://ru.algorithmica.org/cs/shortest-paths/bfs/">алгоритм поиска в ширину</a>.
     *
     * @param start      стартовая ячейка
     * @param targetType целевой тип существ
     * @return путь до цели
     */
    public Cell getTargetPath(Cell start, Class<? extends Entity> targetType) {
        Set<Cell> burningCells = new HashSet<>();
        Queue<Cell> queue = new ArrayDeque<>();

        queue.add(start);
        burningCells.add(start);
        while (!queue.isEmpty()) {
            start = queue.poll();

            var targetNeighbours = getNeighbours(start, targetType);
            if (!targetNeighbours.isEmpty()) {
                return targetNeighbours.stream().findAny().get();
            }
            var emptyNeighbours = getNeighbours(start, null);
            emptyNeighbours.removeAll(burningCells);

            if (!emptyNeighbours.isEmpty()) {
                queue.addAll(emptyNeighbours);
                burningCells.addAll(emptyNeighbours);
            }
        }

        return null;
    }

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        size = width * height;
    }

    public int getEmptyCellCount() {
        return size - entities.size();
    }

    public void turnCreature() {
        for (var e : entities.values()) {
            if (e instanceof Runnable) {
                ((Runnable) e).run();
            }

        }

        for (var cell : waitToRemove) {
            var e = get(cell);
            if (e.isPresent()) {
                var c = e.get().cell;
                entities.replace(e.get().cell, e.get());
                entities.remove(c);
            }
        }
    }

    Stream<Entity> getEntities(Class<? extends Entity> type) {
        return entities.values().stream().filter(type::isInstance);
    }

    public Optional<Entity> get(Cell c) {
        return Optional.ofNullable(entities.get(c));
    }

    public void put(Entity e, Cell cell) {
        e.cell = cell;
        entities.put(cell, e);
    }

    public void move(Entity e, Cell c) {

        entities.replace(c, e);
        entities.remove(e.cell);
        e.cell = c;
    }
}
