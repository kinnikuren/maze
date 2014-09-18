package tests;

import static util.Loggers.log;
import static util.Loggers.programLog;
import static util.Print.print;
import game.core.maze.GateBeans;
import game.core.maze.GateKeeper;
import game.core.maze.GateBeans.GateBean;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GateTest {
    private static Random rand = new Random();

    /*

    public static void main(String[] args) {
        programLog.setLevel(Priority.DORMANT);

        Scanner scanner = new Scanner(System.in);
        String input;
        String name;

        print("Constructing cool Maze...");

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
        name = "Test Guy"; // for testing
        Player you = new Player(maze);
        you.setName(name);


        print(maze.map().viewLinkedNodes());

        ApplicationContext context =
                new ClassPathXmlApplicationContext("beans.xml");

        GateKeeper gk = (GateKeeper) context.getBean("gateKeeper");

        for (GateBean g : gk.getGateSet()) {
            print(g);
            print(g.getGateName());
            print(g.getMaxDistance());
            print(g.getMaxSpawn());
            print(g.isSpawnKey());
        }
        //print(gk.getGateSet());

        //GateKeeper.buildGates(maze);
        //GateKeeper.buildGates(maze);



        /*
        Coordinates.Paired pair = GateKeeper.getGateCoords(maze, 1.0);
        print(pair.c1);
        print(maze.viewNeighborsOf(pair.c1));
        print(pair.c2);
        */

        //Coordinate center = maze.center();

        //print(center.getNeighboringPrimes()[0]);

        /*

        Coordinate gateCoord;
        gateCoord = maze.exit();

        print(maze.viewNeighborsOf(gateCoord));
        Object[] neighbors = maze.viewNeighborsOf(gateCoord).toArray();
        print(neighbors[rand.nextInt(neighbors.length)]);

        Coordinate neighbor = (Coordinate)neighbors[rand.nextInt(neighbors.length)];
                //center.getNeighboringPrimes()[3];

        maze.Trinkets.Key key = maze.map().addLockedGate(gateCoord, neighbor);

        print(key.toString());

        Room r = maze.getRoom(gateCoord);
        r.describeRoom();

        for (Gate g : maze.map().getGates()) {
            print(g.inspect());
        }

        you.whereCanIGo(maze);

        print(maze.map().unlockGate(gateCoord, neighbor, key));
        you.whereCanIGo(maze);
        r.describeRoom();

        you.unlockGate();

        r.describeRoom();

        for (Gate g : maze.map().getGates()) {
            print(g.inspect());
        }

        you.setLocation(neighbor);
        r.describeRoom();
         */

        /*
        print("\nType 'commands' for a list of commands.");

        do {
            print("\nPlease enter a command: ");
            input = scanner.nextLine();
            GameInputHandler.run(input, you, maze);

        } while((Commands.get(input) != Commands.EXITMAZE) && you.isAlive() && !Win.didI());

        print("\nHere is your path: ");
        for(Coordinate c : you.getPath()) {
            System.out.print(" -> " + c);
        }
        scanner.close();
        */

}
