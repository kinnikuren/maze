package maze;

import java.lang.reflect.*;
import java.util.*;

import static util.Loggers.*;
import static util.Print.*;

public class RunMazeTest {
    public static void main(String[] args) {
        //Logger logger = Loggers.programLog;
        programLog.setLevel(Priority.HIGH);

        Scanner scanner = new Scanner(System.in);
        String input;
        String name;

        print("Constructing cool Maze...");

        HashSet<Coordinate> openSet = new HashSet<Coordinate>();
        Coordinate center = new Coordinate();
        openSet.add(center);
        openSet.add(new Coordinate(0,1));
        Maze coolMaze = MazeFactory.buildMaze(openSet, 30, center);

        //Maze coolMaze = MazeFactory.buildMaze(30);
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

        you.getRoom().visitedBy(you);

        you.narrator().speakIntro(you);
        you.narrator().talksAboutRoom(you, you.getRoom());

        //EncounterHandler.run(you);
        //Parade Parade = new Parade();
        //Parade.indyBallTrap(you);

        //for (int i=0;i<10;i++) {
        //EncounterGenerator.run(you);
        //}
        //you.tracker().printMap();
        //you.setStatus("rabies");
        //you.setStatus("the zombie virus");
        //you.setStatus("dysentery");

        print("\nType 'commands' for a list of commands.");

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
