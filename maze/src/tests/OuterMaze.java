package tests;
import game.core.maze.AbstractRoom;
import game.core.positional.Coordinate;
import maze.*;

public class OuterMaze {
    private int size;

    public OuterMaze(int size) {
        this.size = size;
    }

    public int size() { return size; }

    public InnerRoom buildInnerRoom(Coordinate c) {
        return new InnerRoom(c);
    }

    public InnerRoom getInnerRoom(Coordinate c) {
        return null;
    }

    private class InnerRoom extends AbstractRoom {
        InnerRoom(Coordinate c) {
            super(c);
        }

        public void populateRoom() { }

        public OuterMaze outerMaze() { return OuterMaze.this; }
    }

}
