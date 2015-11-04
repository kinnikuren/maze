package game.core.maze;

import static util.Loggers.*;
import game.content.general.SpawningPool;
import game.core.interfaces.Actor;
import game.core.pathfinding.Pathfinder;
import game.core.positional.Coordinate;
import game.objects.obstacles.Cable;
import game.objects.obstacles.RopeBridge;
import game.objects.obstacles.TheDarkness;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import util.Utilities.InOut;

public class PopulateRoom {
    private static Random rand = new Random();

    public static void populify(Maze maze) {
        ApplicationContext context =
            new ClassPathXmlApplicationContext("beans.xml");

        RoomPopulator pr = (RoomPopulator) context.getBean("roomPopulator");
        GateKeeper gk = (GateKeeper) context.getBean("gateKeeper");

        //System.out.println(pr.getSpawnSet());

        RopeBridge.addBridge(maze);
        Coordinate cableLocation = Cable.addCable(maze);

        //spawn darkness
        Set<Coordinate> darknessSet = maze.getCandidateSet(maze.exit(), 0.3, InOut.IN);
        Coordinate darknessLocation;

        Coordinate[] darknessLocs =
                darknessSet.toArray(new Coordinate[darknessSet.size()]);

        darknessLocation = darknessLocs[rand.nextInt(darknessLocs.length)];
        log("Spawning The Darkness at " + darknessLocation + "...");
        TheDarkness dk = new TheDarkness(maze, maze.getRoom(darknessLocation));

        HashSet<Coordinate> reachableAfterDarkness = Pathfinder.findReachableAfterLock(maze, darknessLocation);
        log("Reachable after Darkness added: " + reachableAfterDarkness);

        for (SpawningPool.Spawner s : pr.getSpawnSet()) {
            Actor spawnee = s.spawn();
            log("Attempting to spawn " + spawnee + "...");

            double spawnChance = 1.0;

            int maxSpawn = spawnee.getMaxSpawn();
            String rarity = spawnee.getRarity();
            log("Max spawn is " + maxSpawn);
            log("Rarity is " + rarity);

            Double distanceFactor;
            if (rarity.equals("rare")) {
                distanceFactor = 0.2;
            } else if (rarity.equals("medium-rare")) {
                distanceFactor = 0.35;
            } else if (rarity.equals("pretty common")) {
                distanceFactor = 1.0;
                spawnChance = 0.8;
            } else if (rarity.equals("medium")) {
                distanceFactor = 1.0;
                spawnChance = 0.5;
            }
            else {
                distanceFactor = 1.0;
            }

            HashSet<Coordinate> coordinateSet;

            //darkness dependent spawns
            if (rarity.equals("cable")) {
                coordinateSet = Pathfinder.findReachableAfterLock(maze, cableLocation);
            } else if (rarity.equals("dd")) {
                coordinateSet = reachableAfterDarkness;
            } else {
                coordinateSet = maze.getCandidateSet(maze.exit(), distanceFactor, InOut.IN);
            }

            Coordinate spawnPoint;

            Coordinate[] spawnPoints =
                    coordinateSet.toArray(new Coordinate[coordinateSet.size()]);
            /*
            System.out.print("Possible spawn points: ");
            for (Coordinate c : spawnPoints) {
                 System.out.print(c);
            }

            System.out.println(""); */

            if (coordinateSet.size() > 0) {
                for (int i=0;i < maxSpawn;i++) {
                    double randDouble = rand.nextInt(101) * 0.01;

                    if (randDouble < spawnChance) {
                        int randNum = rand.nextInt(coordinateSet.size());
                        spawnPoint = spawnPoints[randNum];

                        log("Spawning " + spawnee + "...");
                        maze.getRoom(spawnPoint).addActor(s.spawn());
                    }
                }
            }
        }

        gk.buildGates(maze);

        log("Done spawning!");
        maze.printMazeContents();
    }
}
