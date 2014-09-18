package game.objects.units;

import game.core.maze.Maze;
import game.core.positional.Cardinals;
import game.core.positional.Coordinate;

public abstract class AbstractUnitFighterMover extends AbstractUnitFighter {

    public AbstractUnitFighterMover() {
        super();
    }

    public AbstractUnitFighterMover(Coordinate c) {
        super(c);
    }

    public abstract void whereCanIGo(Maze maze);
    public abstract boolean move(Cardinals move, Maze maze);
}
