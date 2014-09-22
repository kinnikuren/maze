package game.core.runtest;

import game.core.events.Priority;
import game.core.inputs.Commands;
import game.core.inputs.GameInputHandler;
import game.core.maze.GateKeeper;
import game.core.maze.KeyMaster;
import game.core.maze.Maze;
import game.core.maze.MazeFactory;
import game.core.maze.MazeMap.*;
import game.core.maze.Win;
import game.core.positional.Coordinate;
import game.objects.obstacles.TheDarkness;
import game.objects.units.Player;
import game.objects.units.Bestiary.Skeleton;
import game.objects.units.Bestiary.Rat;
import game.player.util.Statistics;

import java.util.*;

import static util.Loggers.*;
import static util.Print.*;
import static game.objects.items.Trinkets.*;
import static game.objects.items.Useables.*;
import static game.objects.items.Weapons.*;

public class RunMazeTest {
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
        Maze coolMaze = MazeFactory.buildMaze(openSet, 30, center);

        Statistics.initialize();

        //Maze coolMaze = MazeFactory.buildMaze(30);

        //coolMaze.setExit(new Coordinate(1,1));
        log("Exit location => " + coolMaze.exit());
        log("Center location => " + coolMaze.center());
        log("Final size: " + coolMaze.size());

        coolMaze.populateRooms();


        //AStar.discover(coolMaze);
        print("\nMaze Run Test 4.0 :: Maze project.");
        print("Ready for user input.");

        print("------------------");
        print("------------------");
        print("What is your name? ");
         //name = scanner.nextLine();
        name = "Test Guy"; // for testing
        Player you = new Player(coolMaze);
        you.setName(name);

        print("\nWelcome to the Maze, " + you.name() + "!");

        /*
        you.getInventory().add(new BronzeCoin());
        you.getInventory().add(new GoldenStatue());
        you.getInventory().add(new RedKey());
        you.getInventory().add(new BlueKey());
        you.getInventory().add(new Matches());
        you.getInventory().add(new WoodenStick());
        you.getInventory().add(new OilyRag());
        */
        you.inventory().add(new DissolvingPotion());
        you.getRoom().addActor(new Rat());

        /*
        Coordinate c1 = new Coordinate(0,0);
        Coordinate c2 = new Coordinate(0,1);
        //Gate g = GateKeeper.buildGate(coolMaze, c1, c2);
        Gate g = coolMaze.map().new PurpleDoor(c1, c2);
        coolMaze.map().addLockedGate(g);



        you.getRoom().addActor(new PlainKey());
        you.getRoom().addActor(new RedKey());
        you.getRoom().addActor(new BlueKey());
         */

        you.inventory().add(new LitTorch());

        Coordinate c3 = new Coordinate(0,1);
        TheDarkness.addDarkness(coolMaze, c3);


        //coolMaze.getRoom(c3).addActor(new Rat());

        /*
        you.getRoom().setVisitedTrue(you);
            Skeleton sk1 = new Skeleton();
            sk1.inventory().add(new BronzeCoin());
            Skeleton sk2 = new Skeleton();
            sk2.inventory().add(new BronzeCoin());
            sk2.inventory().add(new BronzeCoin());
            sk2.inventory().add(new GoldenStatue());
        you.getRoom().addActor(sk1);
        you.getRoom().addActor(sk2);
        you.getRoom().addActor(new Rat());
        */

        you.narrator().speakIntro(you);
        you.narrator().talksAboutRoom(you, you.getRoom());

        //EncounterHandler.run(you);
        //Parade Parade = new Parade();
        //Parade.indyBallTrap(you);

        //for (int i=0;i<10;i++) {
        //EncounterGenerator.run(you);
        //}
        //you.tracker().printMap();

        print("\nType 'commands' for a list of commands.");

        do {
            print("\nPlease enter a command: ");
            input = scanner.nextLine();
            GameInputHandler.run(input, you, coolMaze);
        } while((Commands.get(input) != Commands.EXITMAZE) && you.isAlive() && !Win.didI());

        print("\nHere is your path: ");
        for(Coordinate c : you.getPath()) {
            System.out.print(" -> " + c);
        }
        scanner.close();
    }
}
