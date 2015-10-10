package game.core.maze;

import static util.Print.print;
import game.core.maze.MazeMap.Gate;
import game.core.pathfinding.AStar;
import game.core.pathfinding.Pathfinder;
import game.core.positional.Coordinate;
import game.core.positional.Coordinates;

import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

import util.Paired;
import game.objects.items.Useables.Key;

public class KeyMaster {
    private static Random rand = new Random();

    public static Coordinate getKeyCoord(Maze maze, Coordinate c1, Coordinate c2) {
        Coordinate keyPoint;
        Paired<Coordinate> coords = new Paired<Coordinate>(c1,c2);
        /*
        Coordinates.Paired coords = new Coordinates.Paired(c1, c2);
         */
        Set<Coordinate> coordSet = Pathfinder.findReachableAfterLock(maze, coords);

        Coordinate[] coordArray = coordSet.toArray(new Coordinate[0]);



        /*
        LinkedList<Coordinate> path1 = null;
        LinkedList<Coordinate> path2 = null;

        path1 = AStar.discover(maze.center(),c1,maze);

        path2 = AStar.discover(maze.center(),c2,maze);
        //log(path1);
        //log(path2);
         */

        /*
        Object[] keyPoints;

        if (path1.size() < path2.size()) {
            keyPoints = path1.toArray();
        } else {
            keyPoints = path2.toArray();
        }
        */

        //keyPoint = (Coordinate)keyPoints[rand.nextInt(keyPoints.length)];

        keyPoint = coordArray[rand.nextInt(coordArray.length)];

        print("Key spawn point: " + keyPoint);

        return keyPoint;
    }

    public static void insertKey(Maze maze, Coordinate c, Key key) {
        maze.getRoom(c).addActor(key);
    }

    public static void insertKey(Maze maze, Gate gate) {
        Coordinate c = getKeyCoord(maze, gate.g.o1, gate.g.o2);

        insertKey(maze, c, gate.getKey());
    }

}
