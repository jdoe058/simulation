package view;

import models.Position;
import models.entity.MovingCreature;
import models.entity.Entity;

public interface View<T>  {
    void add(T s);
    void add(T s, MovingCreature movingCreature, Position position, Entity prey, Position preyPosition);
    void show();
}
