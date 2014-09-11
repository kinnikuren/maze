package tests;

import static util.Print.print;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.HashSet;

import maze.Commands;
import maze.Coordinate;
import maze.GameInputHandler;
import maze.Maze;
import maze.MazeFactory;
import maze.Player;

public class MazeExitTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;
        String name;

        int sizeIncreaseFactor = 5;

        HashSet<Coordinate> positionSet = new HashSet<Coordinate>();

        print("Constructing cool Maze...");

        Coordinate center = new Coordinate();
        positionSet.add(center);
        positionSet.add(new Coordinate(0,1));
        positionSet.add(new Coordinate(1,1));
        positionSet.add(new Coordinate(1,0));
        positionSet.add(new Coordinate(1,-1));
        positionSet.add(new Coordinate(0,-1));
        positionSet.add(new Coordinate(-1,-1));
        positionSet.add(new Coordinate(-1,0));
        positionSet.add(new Coordinate(-1,1));
        positionSet.add(new Coordinate(1,2)); //10th

        Maze coolMaze = MazeFactory.buildMaze(positionSet,
                positionSet.size() + sizeIncreaseFactor, center);

        //Maze coolMaze = MazeFactory.buildMaze(20);

        for (Coordinate c : coolMaze.viewPositions()) {
            print(c + " => neighbors " + coolMaze.viewNeighborsOf(c));
        }

        //coolMaze.printMazeCoordinates();
        print(coolMaze.size());
        /*
        System.out.print("What is your name? ");
        name = scanner.nextLine();
            Player you = new Player(new Coordinate());
            you.setName(name);
        print("\nWelcome to the Maze, " + you.getName() + "!");
        print("Type 'commands' for a list of commands.");

        List<Coordinate> path = new ArrayList<Coordinate>();

        do {
            print("\nPlease enter a command: ");
            input = scanner.nextLine();
            InputHandler.Run(input, you, coolMaze, path);
        } while(Commands.get(input) != Commands.EXITMAZE);

        print("\nHere is your path: ");
        for(Coordinate c : path) {
            System.out.print(" -> " + c);
        } */
    }
}
