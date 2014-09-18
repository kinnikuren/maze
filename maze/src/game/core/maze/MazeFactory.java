package game.core.maze;

import game.core.positional.Coordinate;

import java.util.*;

import static util.Print.*;

public final class MazeFactory {
    private MazeFactory() { } //no instantiation

    private static TreeSet<Integer> mazeIdSet = new TreeSet<Integer>();

    public static Integer newMazeId(Maze newMaze) {
        if (newMaze.mazeId == null) {
            print("Assigning a new maze ID...");
            Integer newMazeId = (mazeIdSet.size() == 0) ? new Integer(1) : mazeIdSet.last()+1;
            mazeIdSet.add(newMazeId);
            return newMazeId;
        } else throw new IllegalArgumentException("MAZE ID ERROR: "
                + "MAZE MUST HAVE UNINITIALIZED ID VALUE.");
    }

    public static Maze buildMaze() {
        Maze newMaze = new Maze();
        Maze.mazify(newMaze);
        return newMaze;
    }

    public static Maze buildMaze(int mazeUpperBound) {
        Maze newMaze = new Maze(mazeUpperBound);
        Maze.mazify(newMaze);
        return newMaze;
    }

    public static Maze buildMaze(HashSet<Coordinate> openSet, int mazeUpperBound,
            Coordinate center) {
        Maze newMaze = new Maze(openSet, mazeUpperBound, center);
        Maze.mazify(newMaze);
        return newMaze;
    }

}
