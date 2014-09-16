package tests;

import static util.Loggers.log;
import static util.Print.print;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import maze.AStar;
import maze.Coordinate;
import maze.Coordinates;
import maze.Interacter;
import maze.Maze;
import maze.MazeFactory;
import maze.Player;
import maze.References;
import maze.Statistics;
import maze.Bestiary.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import util.View;
import util.Views;

public class SpringTest {
    private static Random rand = new Random();

    public static void spawn(Maze maze) {
        int x;
        int y;
        Coordinate randCoordinate;
        boolean doneSpawning = false;

        /*
        for (References ref : References.values()) {
            if(ref.spawnOnce) {
                print(ref);
            }
        }

        for (tests.ReferencesTest ref : tests.ReferencesTest.values())  {
            if(!ref.spawnOnce) {
                print(ref);
            }
            print(ref.getUnit());
        }
        */

        do {
            randCoordinate = getRandCoordinate(maze);
            log("Getting random coordinate... " + randCoordinate);
            x = Math.abs(Coordinates.diff(randCoordinate, maze.exit()).x());
            y = Math.abs(Coordinates.diff(randCoordinate, maze.exit()).y());
            print("Total distance: " + (x+y));
            print("Maze exit: " + maze.exit());

            //Spawn bosses, where total x+y distance less than 4
            if (x+y < 4) {
                maze.getRoom(randCoordinate).addActor(new RatKing());
                doneSpawning = true;
            }

        } while (!doneSpawning);

    }

    public static Coordinate getRandCoordinate(Maze maze) {
        int x;
        int y;
        Coordinate c;
        do {
            x = rand.nextInt(10)-5;
            y = rand.nextInt(10)-5;
            c = new Coordinate(x,y);
        } while (!maze.map().contains(c));
        return c;
    }

    public static void main(String[] args) {
          ApplicationContext context =
                 new ClassPathXmlApplicationContext("beans.xml");

          maze.Bestiary.Goblin obj = (maze.Bestiary.Goblin) context.getBean("goblin");

          obj.getMessage();
          int maxSpawn = obj.getMaxSpawn();
          System.out.println(obj.getMaxSpawn());

          //tests.PopulateRoom pr = new tests.PopulateRoom();

          //System.out.println(pr.getUnit());

          //Interacter i = (Interacter) context.getBean("goblin");

          String name;

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

          //coolMaze.printMazeCoordinatesAndLinks();

          int x;
          int y;
          Coordinate randCoordinate;
          //boolean doneSpawning = false;

          tests.RoomPopulator pr = (tests.RoomPopulator) context.getBean("roomPopulator");

          System.out.println(pr.getSpawnSet());

          for (maze.SpawningPool.Spawner s : pr.getSpawnSet()) {
              Interacter spawnee = s.spawn();
              log("Spawning " + spawnee + "...");

              maxSpawn = spawnee.getMaxSpawn();
              String rarity = spawnee.getRarity();
              log("Max spawn is " + maxSpawn);
              log("Rarity is " + rarity);

              Double distanceFactor=1.0;
              if (rarity.equals("rare")) {
                  distanceFactor = 0.2;
              } else if (rarity.equals("medium-rare")) {
                  distanceFactor = 0.35;
              } else if (rarity.equals("medium")) {
                  distanceFactor = 0.5;
              }
              else {
                  distanceFactor = 1.0;
              }

              HashSet<Coordinate> coordinateSet = coolMaze.getCoordinateSet(distanceFactor,coolMaze.exit());
              Coordinate spawnPoint;

              Coordinate[] spawnPoints =
                      coordinateSet.toArray(new Coordinate[coordinateSet.size()]);

              System.out.print("Possible spawn points: ");
              for (Coordinate c : spawnPoints) {
                   System.out.print(c);
              }
              System.out.println("");

              for (int i=0;i < maxSpawn;i++) {

                  /*
                  randCoordinate = getRandCoordinate(coolMaze);
                  log("Getting random coordinate... " + randCoordinate);
                  x = Math.abs(Coordinates.diff(randCoordinate, coolMaze.exit()).x());
                  y = Math.abs(Coordinates.diff(randCoordinate, coolMaze.exit()).y());
                  print("Total distance: " + (x+y));
                  print("Maze exit: " + coolMaze.exit());
                  */

                  //Interacter actor = (Interacter) context.getBean("goblin");
                  //coolMaze.getRoom(randCoordinate).addActor(pr.spawnUnit());
                  //coolMaze.getRoom(randCoordinate).addActor(actor);

                  //coolMaze.getRoom(randCoordinate).
                  //coolMaze.getRoom(randCoordinate).addActor(new Rat());

                  int rand = new Random().nextInt(coordinateSet.size());
                  spawnPoint = spawnPoints[rand];

                  coolMaze.getRoom(spawnPoint).addActor(s.spawn());


              }
          }
          coolMaze.printMazeContents();

          //coolMaze.printMazeCoordinates();

          //System.out.println(coolMaze.map().viewLinkedNodes());


          for (References ref : References.values()) {
              //Interacter actor = (Interacter) context.getBean(ref.name());
              //System.out.println(actor);
          }

          //print("Random coordinate: " + getRandCoordinate(coolMaze));

       }

}
