package tests;

import static util.Loggers.log;
import static util.Loggers.programLog;
import static util.Print.print;
import game.content.encounters.Parade;
import game.core.events.Priority;
import game.core.maze.Maze;
import game.core.maze.MazeFactory;
import game.core.pathfinding.AStar;
import game.core.positional.Coordinate;
import game.objects.units.Player;
import game.player.util.Statistics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

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
