package game.core.maze;

import static util.Print.print;
import game.core.maze.MazeMap.Gate;
import game.core.pathfinding.AStar;
import game.core.positional.Coordinate;

import java.util.LinkedList;
import java.util.Random;

import game.objects.items.Trinkets.Key;

public class KeyMaster {
    private static Random rand = new Random();

    public static Coordinate getKeyCoord(Maze maze, Coordinate c1, Coordinate c2) {
        Coordinate keyPoint;

        LinkedList<Coordinate> path1 = null;
        LinkedList<Coordinate> path2 = null;

        AStar.reset();
        path1 = AStar.discover(maze.center(),c1,maze);
        AStar.reset();
        path2 = AStar.discover(maze.center(),c2,maze);
        //log(path1);
        //log(path2);

        Object[] keyPoints;

        if (path1.size() < path2.size()) {
            keyPoints = path1.toArray();
        } else {
            keyPoints = path2.toArray();
        }

        keyPoint = (Coordinate)keyPoints[rand.nextInt(keyPoints.length)];

        //print("Key spawn point: " + keyPoint);

        return keyPoint;
    }

    public static void insertKey(Maze maze, Coordinate c, Key key) {
        maze.getRoom(c).addActor(key);
    }

    public static void insertKey(Maze maze, Gate gate) {
        Coordinate c = getKeyCoord(maze, gate.g.c1, gate.g.c2);

        insertKey(maze, c, gate.getKey());
    }

}
