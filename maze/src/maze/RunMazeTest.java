package maze;

import java.util.*;

import util.Logger;
import static util.Loggers.*;
import maze.Maze.Room;
import static util.Print.*;

public class RunMazeTest {
    public static void main(String[] args) {
        //Logger logger = Loggers.programLog;
        programLog.setLevel(Priority.HIGH);
        Scanner scanner = new Scanner(System.in);
        String input;
        String name;

        print("Constructing cool Maze...");

        /* HashSet<Coordinate> openSet = new HashSet<Coordinate>();
        Coordinate center = new Coordinate();
        openSet.add(center);
        openSet.add(new Coordinate(0,1));
        openSet.add(new Coordinate(1,1));
        openSet.add(new Coordinate(1,0));
        //positionSet.add(new Coordinate(4,2));
        openSet.add(new Coordinate(4,3));
        Maze coolMaze = MazeFactory.buildMaze(openSet, openSet.size(), center); */

        Maze coolMaze = MazeFactory.buildMaze(30);
        coolMaze.populateRooms();

        //coolMaze.setExit(new Coordinate(1,1));
        log("Exit location => " + coolMaze.exit());
        log("Center location => " + coolMaze.center());
        log("Final size: " + coolMaze.size());

        List<Coordinate> path = new ArrayList<Coordinate>();
        AStar.discover(coolMaze);
        print("\nMaze Run Test 3.0 :: Maze project.");
        print("Ready for user input.");

        print("------------------");
        print("------------------");
        print("What is your name? ");
         //name = scanner.nextLine();
        name = "Test Guy"; // for testing
            Player you = new Player(coolMaze);
            you.setName(name);
        print("\nWelcome to the Maze, " + you.name() + "!");
        print("Type 'commands' for a list of commands.");

        Narrator.speakIntro(you);

        Narrator.talksAboutRoom(you, you.getRoom());

        //you.setStatus("ebola");

        do {
            print("\nPlease enter a command: ");
            input = scanner.nextLine();
            GameInputHandler.run(input, you, coolMaze, path);
        } while((Commands.get(input) != Commands.EXITMAZE) && you.isAlive());

        print("\nHere is your path: ");
        for(Coordinate c : path) {
            System.out.print(" -> " + c);
        }
        scanner.close();
    }
}
