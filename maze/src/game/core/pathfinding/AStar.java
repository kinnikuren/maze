package game.core.pathfinding;

import game.core.maze.Maze;
import game.core.positional.Coordinate;

import java.util.*;
import java.lang.StringBuilder;

import static util.Utilities.*;
import static util.Loggers.*;

public class AStar {

    public AStar() { }

    public static LinkedList<Coordinate> discover(Maze maze) {
      return discover(maze.center(), maze.exit(), maze);
    }

    public static LinkedList<Coordinate> discover(Coordinate start, Coordinate goal, Maze maze) {
        Coordinate current;
        final HashMap<Coordinate, Double> g = new HashMap<Coordinate, Double>();
        final HashMap<Coordinate, Double> f = new HashMap<Coordinate, Double>();
        final HashMap<Coordinate, Coordinate> paths = new HashMap<Coordinate, Coordinate>();
        final HashSet<Coordinate> closed = new HashSet<Coordinate>();
        final PriorityQueue<Coordinate> open;

        Comparator<Coordinate> fComparator = new Comparator<Coordinate>() {
            @Override
            public int compare(Coordinate c1, Coordinate c2) {
                double i = f.get(c1)-f.get(c2);
              return sign(i);
            }
        };

        checkNullArgs(start, goal, maze);
        boolean discovery = false;
        LinkedList<Coordinate> path = null;
        log("Start is " + start);
        log("Goal is " + goal);

        if (maze.size() == 0) {
          discovery = false;
          log("Maze size is 0.");
        }
        else {
            log("Maze size is " + maze.size());
            open = new PriorityQueue<Coordinate>(maze.size(), fComparator);

            g.put(start,0.0);
            f.put(start, g.get(start)+heuristic(start,goal));
            open.add(start);

            while(!open.isEmpty()) {
                log(open.toString());
              current = open.poll();
              log("Discovery? " + discovery);
              if (current.equals(goal)) {
                discovery = true;
                closed.add(current);
                break;
              }
              closed.add(current);
              for(Coordinate c : maze.viewNeighborsOf(current)) {
                if (closed.contains(c)) continue;

                double temp_g = g.get(current) + current.getDistanceTo(c);
                if (!open.contains(c) || temp_g < g.get(c)) {
                  paths.put(c, current);
                  g.put(c, temp_g);
                  f.put(c, g.get(c) + heuristic(c,goal));
                  if (!open.contains(c)) open.add(c);
                }
              }
            }
            log("Discovery? " + discovery);
            if (discovery) {
              StringBuilder pathline = new StringBuilder();
              path = solvePath(paths,goal,start);
              log("A*: success: path found.");

              for (Iterator<Coordinate> i = path.iterator(); i.hasNext();) {
                Coordinate c = i.next();
                if (i.hasNext()) pathline.append(c + " -> ");
                else pathline.append(c);
              } //String representation of path;
              log(pathline.toString());
            } else {
              log("A*: failure: path not found.");
            }
        }
      return path;
    }

    public static LinkedList<Coordinate> solvePath(HashMap<Coordinate, Coordinate> paths,
            Coordinate goal, Coordinate start) {
        LinkedList<Coordinate> path = new LinkedList<Coordinate>();
        Coordinate current = goal;
        while(!current.equals(start)) {
            path.add(current);
            current = paths.get(current);
        }
        path.add(start);
        path = reverseLinkedList(path);
      return path;
    }

    private static double heuristic(Coordinate current, Coordinate goal) { //A* admissible heuristic
        return current.getDistanceTo(goal);
      }
}
