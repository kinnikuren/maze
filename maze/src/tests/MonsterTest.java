package tests;
import static maze.Commands.APPROACH;
import static maze.Commands.FIGHT;
import static maze.Commands.MOVE;
import static maze.Events.announce;
import static maze.Events.fight;
import static maze.Priority.DEFAULT;
import static maze.Priority.HIGH;
import static maze.Priority.LOW;
import static util.Print.print;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import maze.*;

public class MonsterTest {

    public static class ReanimatingSkeleton extends Bestiary.Skeleton {
        @Override
        public Event interact(Commands trigger) {
            if (!isAlive()) return null;

            return trigger == FIGHT ? Events.fightSkeletonTwice(this, HIGH)
                : (trigger == APPROACH ? announce(this, LOW, inspect())
                : (trigger == MOVE ? announce(this, DEFAULT, "You hear a rattle of bones")
                :  null));
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
         String input;
         String name;

         int sizeIncreaseFactor = 5;

         HashSet<Coordinate> positionSet = new HashSet<Coordinate>();

         print("Constructing cool Maze...");

         Coordinate center = new Coordinate(0,1);
         positionSet.add(center);
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

         for (Coordinate c : coolMaze.viewPositions()) {
             print(c + " => neighbors " + coolMaze.viewNeighborsOf(c));
         }
         print(coolMaze.size());

         ReanimatingSkeleton rsk = new ReanimatingSkeleton();
         coolMaze.getRoom(center).addActor(rsk);

         List<Coordinate> path = new ArrayList<Coordinate>();

         Statistics.initialize();

         System.out.print("What is your name? ");
         name = scanner.nextLine();
             Player you = new Player(coolMaze);
             you.setName(name);
         print("\nWelcome to the Maze, " + you.name() + "!");
         print("Type 'commands' for a list of commands.");

         do {
             print("\nPlease enter a command: ");
             input = scanner.nextLine();
             GameInputHandler.run(input, you, coolMaze, path);
         } while(Commands.get(input) != Commands.EXITMAZE);

         print("\nHere is your path: ");
         for(Coordinate c : path) {
             System.out.print(" -> " + c);
         }
    }

}
