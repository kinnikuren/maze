package game.core.maze;

import static util.Loggers.*;
import static game.core.positional.Coordinates.*;
import game.core.positional.Cardinals;
import game.core.positional.Coordinate;
import game.core.positional.Coordinates;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public final class MazeGenerator {
    private MazeGenerator() { } //no instantiation

    public static MazeMap mappify(Maze maze) {
        return MazeGenerator.mappify(maze.map(),
                maze.openSet(), maze.sizeUpperBound(),
                maze.spawnSeedFactor, maze.diagFactor);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static MazeMap mappify(MazeMap mazeMap,
            HashSet<Coordinate> openSet, int sizeUpperBound,
            int seed, int diagFactor) {

        //generate spawnedSet from openSet, add to finalSet
        HashSet<Coordinate> spawnedSet = new HashSet<Coordinate>();
        HashSet<Paired> diagSet = new HashSet<Paired>();

        int spawnFactor = seed;
        int count = 0; //loop count

        generating_loop:
        while (mazeMap.size() < sizeUpperBound) {
          if (openSet.size() == 0) break generating_loop; //breaks full loop if no unprocessed nodes remain

          for (Iterator<Coordinate> i = openSet.iterator(); i.hasNext();) {
            Coordinate c = i.next();
            if (mazeMap.add(c)) //adds coordinate node to final room map
                log("Position added to final position set with coordinates: " + c);
            if (mazeMap.size() >= sizeUpperBound) {
                log("Ending the maze spawn loop as the upper bound has been reached.");
                openSet.clear();
                spawnedSet.clear();
                break generating_loop;
            }

            Random rand = new Random(); //randomly remove open nodes from processing
            if (mazeMap.size() <= 10) {
                spawnFactor = seed; //spawn factor 1/2 with default spawn seed of 2
            }
            else if (mazeMap.size() <= 20) {
                spawnFactor = seed + rand.nextInt(seed + 1);
            }
            else {
                spawnFactor = seed + rand.nextInt(seed + 2); //spawn factor decreases as size increases
            }

            Coordinate[] neighbors = c.getNeighboringPrimes(); //retrieves neighbors of processed node
            for (Coordinate n : neighbors) {
              if (!mazeMap.contains(n)) { //don't process neighbor node if map already contains it

                Integer randomOnSwitch = 1 + rand.nextInt(spawnFactor);
                if (randomOnSwitch == 1) { //use random switch to filter additions to map
                    spawnedSet.add(n);
                } else log("Coordinate " + n + " was discarded by maze generation engine.");
              }
            }
            Integer diagOnSwitch = 1 + rand.nextInt(diagFactor);
              if (diagOnSwitch == 1) {
                Cardinals[] diagDir = Coordinates.LESSER_CARDINALS.toArray(new Cardinals[0]);
                Integer pickOne = rand.nextInt(diagDir.length);
                Cardinals direction = diagDir[pickOne];
                int xDistance = rand.nextInt(3) + 1;
                int yDistance = rand.nextInt(2) + 1;
                Coordinate nDiag = new Coordinate(c, direction, xDistance, yDistance);
                diagSet.add(new Paired(c, nDiag));
                if (!mazeMap.contains(nDiag)) spawnedSet.add(nDiag);
              } //end diagonal switch
            i.remove(); //clears out processed node
          } //prepare for next loop
          openSet = new HashSet(spawnedSet); //assigns newly spawned nodes to openSet for processing
          spawnedSet.clear(); //clears nodes from spawnedSet
          ++count;
        }
        //..
        //log("MAZE MAP POSITION SET");
        //for (Coordinate c : mazeMap.viewAllNodes()) log(c);
        //..
        mazeMap.deriveStandardLinks();
        for (Paired diags : diagSet) {
            try {
              String result = mazeMap.linkDouble(diags.c1, diags.c2) ? "was successfully" : "could not be";
              log("Diagonal " + result + " generated between " + diags.c1 + " and " + diags.c2);
            } catch (IllegalArgumentException iae) {
                boolean exists = mazeMap.containsAll(diags.c1, diags.c2);
                if (!exists) log("Diagonal between " + diags.c1 + " and " + diags.c2
                        + " failed because a position was discarded.");
            }
        }
            /* log("GENERATOR MAZE MAP LINKS------");
            for (Coordinate c : mazeMap.viewLinkedNodes()) {
                log(c + " => " + mazeMap.viewNeighborsOf(c));
            } */
            //..
      return mazeMap;
    }
}
