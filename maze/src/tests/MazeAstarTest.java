package tests;

import game.core.inputs.Commands;
import game.core.inputs.GameInputHandler;
import game.core.maze.Maze;
import game.core.maze.MazeFactory;
import game.core.pathfinding.AStar;
import game.core.positional.Coordinate;
import game.objects.units.Player;

import java.util.*;
import java.io.*;

import static util.Print.*;

public class MazeAstarTest {
    public static void main(String[] args) {

        HashSet<Coordinate> positionSet = new HashSet<Coordinate>();

        print("Constructing cool Maze...");
        Coordinate center = new Coordinate();
        positionSet.add(center);
        positionSet.add(new Coordinate(-1,1));
        positionSet.add(new Coordinate(-1,0));
        positionSet.add(new Coordinate(-1,-1));
        positionSet.add(new Coordinate(-1,-2));
        positionSet.add(new Coordinate(0,3));
        positionSet.add(new Coordinate(0,2));
        positionSet.add(new Coordinate(0,1));
        positionSet.add(new Coordinate(0,-1));

        positionSet.add(new Coordinate(1,3));
        positionSet.add(new Coordinate(1,0));
        positionSet.add(new Coordinate(1,-2));

        positionSet.add(new Coordinate(2,3));
        positionSet.add(new Coordinate(2,2));
        positionSet.add(new Coordinate(2,1));
        positionSet.add(new Coordinate(2,-1));
        positionSet.add(new Coordinate(2,-2));

        positionSet.add(new Coordinate(3,1));
        positionSet.add(new Coordinate(3,0));
        positionSet.add(new Coordinate(3,-1));
        positionSet.add(new Coordinate(3,-2));

        Maze coolMaze = MazeFactory.buildMaze(positionSet, positionSet.size(), center);
        coolMaze.setExit(new Coordinate(1,-2));
        coolMaze.setCenter(new Coordinate());
        print("Exit location => "); print(coolMaze.exit());

        coolMaze.printMazeCoordinatesAndLinks();
        print(coolMaze.size());
        AStar.discover(coolMaze);


        Scanner scanner = new Scanner(System.in);
        String input;
        String name;
        System.out.print("What is your name? ");
        name = scanner.nextLine();
            Player you = new Player(coolMaze);
            you.setName(name);
        print("\nWelcome to the Maze, " + you.name() + "!");
        print("Type 'commands' for a list of commands.");
        List<Coordinate> path = new ArrayList<Coordinate>();


        do {
            print("\nPlease enter a command: ");
            input = scanner.nextLine();
            GameInputHandler.run(input, you, coolMaze);
        } while(Commands.get(input) != Commands.EXITMAZE);

        print("\nHere is your path: ");
        for(Coordinate c : you.getPath()) {
            System.out.print(" -> " + c);
        }
        scanner.close();
    }
}

