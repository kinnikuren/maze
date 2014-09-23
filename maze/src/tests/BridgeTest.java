package tests;

import game.core.maze.Maze;
import game.core.maze.MazeDisplay;
import game.core.maze.MazeFactory;
import game.core.positional.Coordinate;
import game.objects.units.Player;
import game.player.util.Statistics;
import static util.Loggers.*;
import static util.Print.*;

import java.util.HashSet;

public class BridgeTest {

    public static void main(String[] args) {
        HashSet<Coordinate> openSet = new HashSet<Coordinate>();
        Coordinate center = new Coordinate();
        openSet.add(center);
        openSet.add(new Coordinate(0,1));
        Maze maze = MazeFactory.buildMaze(openSet, 30, center);

        Statistics.initialize();

        maze.populateRooms();
        Maze.Room room = maze.getRoom(center);

        //coolMaze.setExit(new Coordinate(1,1));
        log("Exit location => " + maze.exit());
        log("Center location => " + maze.center());
        log("Final size: " + maze.size());


        //AStar.discover(coolMaze);
        print("\nMaze Run Test 3.0 :: Maze project.");
        print("Ready for user input.");

        print("------------------");
        print("------------------");
        print("What is your name? ");
         //name = scanner.nextLine();
        String name = "Test Guy"; // for testing
        Player you = new Player(maze);
        you.setName(name);

        print(maze.map().viewLinkedNodes());
        print(maze.map().viewTwoLinkNodes());

        //MazeDisplay.run(maze, you);

    }

}
