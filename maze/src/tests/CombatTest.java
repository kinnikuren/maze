package tests;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import maze.AStar;
import maze.Coordinate;
import maze.Maze;
import maze.MazeFactory;
import maze.Module;
import maze.Parade;
import maze.Player;
import maze.Priority;
import maze.Statistics;
import static maze.Bestiary.*;
import static maze.Consumables.*;
import static maze.Weapons.*;
import static util.Loggers.programLog;

public class CombatTest {

    public static void main(String[] args) {
        String name;

        programLog.setLevel(Priority.HIGH);

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

        you.getInventory().add(new HealingPotion());
        you.getInventory().add(new HealingPotion());

        you.getPaperDoll().add(new LongSword());

        List<Monster> monsters = new ArrayList<Monster>();
        monsters.add(new Rat());
        monsters.add(new Skeleton());
        monsters.add(new Rat());
        monsters.add(new RabidRat());

        maze.InteractionHandler.run(monsters, you, new Module.Fight());

        //maze.InteractionHandler.run(new Skeleton(), you, new Module.Fight());
    }
}
