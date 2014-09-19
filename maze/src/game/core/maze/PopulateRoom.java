package game.core.maze;

import static util.Loggers.*;
import game.content.general.SpawningPool;
import game.core.interfaces.Actor;
import game.core.positional.Coordinate;

import java.util.HashSet;
import java.util.Random;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PopulateRoom {
    private static Random rand = new Random();

    public static void run(Maze maze) {
        ApplicationContext context =
            new ClassPathXmlApplicationContext("beans.xml");

        RoomPopulator pr = (RoomPopulator) context.getBean("roomPopulator");
        GateKeeper gk = (GateKeeper) context.getBean("gateKeeper");

        //System.out.println(pr.getSpawnSet());

        for (SpawningPool.Spawner s : pr.getSpawnSet()) {
            Actor spawnee = s.spawn();
            log("Spawning " + spawnee + "...");

            int maxSpawn = spawnee.getMaxSpawn();
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

            HashSet<Coordinate> coordinateSet = maze.getCoordinateSet(distanceFactor,maze.exit());
            Coordinate spawnPoint;

            Coordinate[] spawnPoints =
                    coordinateSet.toArray(new Coordinate[coordinateSet.size()]);
            /*
            System.out.print("Possible spawn points: ");
            for (Coordinate c : spawnPoints) {
                 System.out.print(c);
            }

            System.out.println(""); */

            for (int i=0;i < maxSpawn;i++) {
                int rand = new Random().nextInt(coordinateSet.size());
                spawnPoint = spawnPoints[rand];

                maze.getRoom(spawnPoint).addActor(s.spawn());
            }
        }

        //gk.buildGates(maze);

        log("Done spawning!");
        maze.printMazeContents();
    }
}
