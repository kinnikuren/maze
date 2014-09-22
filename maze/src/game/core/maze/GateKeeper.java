package game.core.maze;

import static util.Loggers.log;
import static util.Print.print;
import static game.core.maze.GateBeans.*;
import game.core.maze.GateBeans.GateBean;
import game.core.maze.MazeMap.Gate;
import game.core.positional.Coordinate;
import game.core.positional.Coordinates;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GateKeeper {
    private static Random rand = new Random();
    private Set<GateBean> gateSet;

    public static Coordinates.Paired getGateCoords(Maze maze, Double gateDistanceFactor) {
        Coordinate spawnPoint;
        Coordinate neighborPoint;
        int randNum;

        HashSet<Coordinate> coordinateSet = maze.getCoordinateSet(gateDistanceFactor,maze.exit());

        Coordinate[] spawnPoints =
                coordinateSet.toArray(new Coordinate[coordinateSet.size()]);

        randNum = new Random().nextInt(coordinateSet.size());
        spawnPoint = spawnPoints[randNum];

        Object[] neighbors = maze.viewNeighborsOf(spawnPoint).toArray();
        neighborPoint = (Coordinate)neighbors[rand.nextInt(neighbors.length)];

        Coordinates.Paired gateCoords = new Coordinates.Paired(spawnPoint, neighborPoint);
        log("Gate to be spawned at " + spawnPoint + " and " + neighborPoint);

        return gateCoords;
    }

    public static Gate buildGate(Maze maze, Coordinate c1, Coordinate c2) {
        return maze.map().new Gate(c1, c2);
    }

    public void buildGates(Maze maze) {
        List<Gate> gateList = new ArrayList<Gate>();

        for (GateBean g : gateSet) {
            Double maxDistance = g.getMaxDistance();
            String gateName = g.getGateName();
            int maxSpawn = g.getMaxSpawn();
            Gate gate = null;

            for (int i=0;i < maxSpawn; i++) {

                //get spawn points for gate
                Coordinates.Paired gateCoords = getGateCoords(maze, maxDistance);

                //create gate
                //Gate gate = buildGate(maze, gateCoords.c1, gateCoords.c2);
                if (gateName.equals("Gate")) {
                    gate = maze.map().new Gate(gateCoords.c1, gateCoords.c2);
                } else if (gateName.equals("Red Door")) {
                    gate = maze.map().new RedDoor(gateCoords.c1, gateCoords.c2);
                } else if (gateName.equals("Blue Door")) {
                    gate = maze.map().new BlueDoor(gateCoords.c1, gateCoords.c2);
                } else if (gateName.equals("Purple Door")) {
                    gate = maze.map().new PurpleDoor(gateCoords.c1, gateCoords.c2);
                }

                if (g.isSpawnKey()) {
                    KeyMaster.insertKey(maze, gate);
                }

                //add gate to list
                gateList.add(gate);
            }
        }
        for (Gate g : gateList) {
            //add gate to maze
            maze.map().addLockedGate(g);
        }

    }

    public Set<GateBean> getGateSet() {
        return gateSet;
    }

    public void setGateSet(Set<GateBean> gateSet) {
        this.gateSet = gateSet;
    }

    /*
    public static void main(String[] args) {
        Coordinate c1 = new Coordinate(99,99);
        Coordinate c2 = new Coordinate(98,99);

        Maze m = new Maze();

        Gate g = buildGate(m,c1,c2);

        print(g.g.c1);
        print(g.g.c2);

        m.getRoom(m.center()).addActor(g);

        m.getRoom(m.center()).describeRoom();

        //m.map().addLockedGate(g);
    }
    */

}
