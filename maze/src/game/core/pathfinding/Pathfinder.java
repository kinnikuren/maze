package game.core.pathfinding;
import static util.Print.print;
import static util.Utilities.*;
import game.core.maze.Maze;
import game.core.maze.Maze.Room;
import game.core.positional.Coordinate;
import game.core.positional.Coordinates;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import util.NullArgumentException;
import util.View;

public class Pathfinder {
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static HashSet<Coordinate> findReachable(Maze maze) {
        checkNullArg(maze);
        checkNullArg(maze.center());

        HashSet<Coordinate> reachableSet = new HashSet<Coordinate>();
        Coordinate center = maze.center();
        HashSet<Coordinate> openSet = new HashSet<Coordinate>();
        HashSet<Coordinate> newSet = new HashSet<Coordinate>();
        reachableSet.add(center);
        for (Coordinate n : maze.viewNeighborsOf(center)) {
            openSet.add(n);
            reachableSet.add(n);
        }
        do {
            for (Iterator<Coordinate> i = openSet.iterator(); i.hasNext();) {
                Coordinate c = i.next();
                reachableSet.add(c);
                for (Coordinate n : maze.viewNeighborsOf(c)) {
                    if (reachableSet.add(n)) newSet.add(n);
                }
                i.remove();
            }
            openSet = new HashSet(newSet);
            newSet.clear();
        } while(openSet.size() != 0);
      return reachableSet;
    }

    public static HashSet<Coordinate> findUnreachable(Maze maze) {
        HashSet<Coordinate> reachableSet = findReachable(maze);
        HashSet<Coordinate> unreachableSet = new HashSet<Coordinate>();
        for (Coordinate c : maze.viewPositions()) {
            if (!reachableSet.contains(c)) unreachableSet.add(c);
        }
      return unreachableSet;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static HashSet<Coordinate> findReachableAfterLock(Maze maze, Coordinates.Paired locked) {
        checkNullArg(maze);
        checkNullArg(maze.center());

        HashSet<Coordinate> reachableSet = new HashSet<Coordinate>();
        Coordinate center = maze.center();
        HashSet<Coordinate> openSet = new HashSet<Coordinate>();
        HashSet<Coordinate> newSet = new HashSet<Coordinate>();

        /*reachableSet.add(center);
        for (Coordinate n : maze.viewNeighborsOf(center)) {
          if (!locked.matches(center, n)) {
            if (reachableSet.add(n)) openSet.add(n);
          }
        } */
        openSet.add(center);
        do {
            for (Iterator<Coordinate> i = openSet.iterator(); i.hasNext();) {
              Coordinate c = i.next();
              reachableSet.add(c);
              for (Coordinate n : maze.viewNeighborsOf(c)) {
                if (!locked.matches(c, n)) {
                  if (reachableSet.add(n)) newSet.add(n);
                }
              }
              i.remove();
            }
            openSet = new HashSet(newSet);
            newSet.clear();
        } while(openSet.size() != 0);

      return reachableSet;
    }

    public static HashSet<Coordinate> findUnreachableAfterLock(Maze maze, Coordinates.Paired locked) {
        HashSet<Coordinate> reachableSet = findReachableAfterLock(maze, locked);
        HashSet<Coordinate> unreachableSet = new HashSet<Coordinate>();
        for (Coordinate c : maze.viewPositions()) {
            if (!reachableSet.contains(c)) unreachableSet.add(c);
        }
      return unreachableSet;
    }

    public static boolean isExitReachable(Maze maze) {
        checkNullArg(maze);
        checkNullArg(maze.exit());

      return findReachable(maze).contains(maze.exit());
    }
}
