package tests;

import static util.Loggers.log;
import static util.Loggers.programLog;
import static util.Print.print;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import maze.AStar;
import maze.Coordinate;
import maze.Maze;
import maze.MazeFactory;
import maze.Parade;
import maze.Player;
import maze.Priority;
import maze.Statistics;

public class EncounterTest {

    public static void main(String[] args) {
        String name;

        HashSet<Coordinate> openSet = new HashSet<Coordinate>();
        Coordinate center = new Coordinate();
        openSet.add(center);
        openSet.add(new Coordinate(0,1));
        Maze coolMaze = MazeFactory.buildMaze(openSet, 30, center);

        Statistics.initialize();

        //Maze coolMaze = MazeFactory.buildMaze(30);
        coolMaze.populateRooms();

        List<Coordinate> path = new ArrayList<Coordinate>();
        AStar.discover(coolMaze);

        name = "Test Guy"; // for testing
        Player you = new Player(coolMaze);
        you.setName(name);

        Parade Parade = new Parade();
        Parade.fineas(you, "fineas");
    }

}
