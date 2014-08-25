package maze;

import java.util.*;
import java.lang.StringBuilder;

import util.NullArgumentException;
import static util.Print.*;
import static util.Utilities.*;
import static util.Loggers.*;

public class AStar {
    private static Coordinate current;
    private static HashMap<Coordinate, Double> g = new HashMap<Coordinate, Double>();
    private static HashMap<Coordinate, Double> f = new HashMap<Coordinate, Double>();
    private static HashMap<Coordinate, Coordinate> paths = new HashMap<Coordinate, Coordinate>();
    private static HashSet<Coordinate> closed = new HashSet<Coordinate>();
    private static PriorityQueue<Coordinate> open;

    private Maze maze;
    private LinkedList<Coordinate> pathList = new LinkedList<Coordinate>();

    public AStar(Maze maze) {
        this.maze = maze;
    }

    public static Comparator<Coordinate> fComparator = new Comparator<Coordinate>() {
        @Override
        public int compare(Coordinate c1, Coordinate c2) {
            double i = f.get(c1)-f.get(c2);
          return sign(i);
        }
    };

    public static double h(Coordinate current, Coordinate goal) { //A* admissible heuristic
      return current.getDistanceTo(goal);
    }

    public static LinkedList<Coordinate> discover(Maze maze)
                throws NullArgumentException {
      return discover(maze.center(), maze.exit(), maze);
    }

    public static LinkedList<Coordinate> discover(Coordinate start, Coordinate goal, Maze maze)
                throws NullArgumentException {
        checkNullArgs(start, goal, maze);
        boolean discovery = false;
        LinkedList<Coordinate> path = null;

      if (maze.size() == 0) discovery = false;
      else {
        open = new PriorityQueue<Coordinate>(maze.size(), fComparator);

        g.put(start,0.0);
        f.put(start, g.get(start)+h(start,goal));
        open.add(start);

        while(!open.isEmpty()) {
          current = open.poll();
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
              f.put(c, g.get(c) + h(c,goal));
              if (!open.contains(c)) open.add(c);
            }
          }
        }
        if (discovery) {
          StringBuilder pathline = new StringBuilder();
          path = solvePath(paths,goal,start);
          print("A*: success: path found.");

          for (Iterator<Coordinate> i = path.descendingIterator(); i.hasNext();) {
            Coordinate c = i.next();
            if (i.hasNext()) pathline.append(c + " -> ");
            else pathline.append(c);
          } //String representation of path;
          log(pathline.toString());
        } else {
          print("A*: failure: path not found.");
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
      return path;
    }
}
