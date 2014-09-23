package tests;

import game.content.encounters.Parade;
import game.core.events.Module;
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

import static game.objects.items.Consumables.*;
import static game.objects.items.Weapons.*;
import static game.objects.units.Bestiary.*;
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

        you.inventory().add(new HealingPotion());
        you.inventory().add(new HealingPotion());

        you.paperDoll().add(new Longsword());

        List<Monster> monsters = new ArrayList<Monster>();
        monsters.add(new Rat());
        monsters.add(new Skeleton());
        monsters.add(new Rat());
        monsters.add(new RabidRat());

        game.core.events.InteractionHandler.run(monsters, you, new Module.Fight());

        //maze.InteractionHandler.run(new Skeleton(), you, new Module.Fight());
    }
}
