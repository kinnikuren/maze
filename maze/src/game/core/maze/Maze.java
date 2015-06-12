package game.core.maze;

import game.core.pathfinding.AStar;
import game.core.positional.Cardinals;
import game.core.positional.Coordinate;

import java.util.*;

import util.View;
import util.Utilities.InOut;
import static util.Print.*;
import static util.Loggers.*;

public class Maze {
    protected static void mazify(Maze maze) { // Maze only generates if openSet exists
        if (maze.openSet.size() > 0) {
            print("Preparing to mazify...");
            MazeGenerator.mappify(maze);
            RoomMapMaker.roomify(maze);
            //maze.populateRooms();
            if (maze.exit() == null) maze.findExit();
            maze.setAstarPathToExit();
            print("Mazification complete.");
        } else throw new IllegalArgumentException("MAZIFY ERROR: "
                    + "CANNOT MAZIFY WITH NO OPEN POSITION SET.");
    }

    public class Room extends AbstractRoom {
        private Room(Coordinate c) {
            super(c);
        }
        @Override
        public void populateRoom() {
            //for testing
            //tests.PopulateRoomTest.fillerUp(position, center, this);
        }
        public View<Coordinate> viewNeighbors() {
          return Maze.this.viewNeighborsOf(position);
        }
    }

    private HashSet<Coordinate> openSet = new HashSet<Coordinate>(); //set of positions to use for generation
    private MazeMap map = new MazeMap(); //set of final positions and links
    private Coordinate center = null;
    private Coordinate exit = null;
    private LinkedList<Coordinate> path = null;
    public final Integer mazeId;
    private int sizeUpperBound;
    private boolean resizable = false;
    public final int sizeRandomFactor = 12;
    public final int sizeConstantFactor = 10;
    public final int spawnSeedFactor = 2;
    public final int diagFactor = 3;
    public final Double exitFactor = 0.5; //defines minimum distance for exit relative to maxDistance

    protected Maze() {
        this.mazeId = MazeFactory.newMazeId(this);
        this.sizeUpperBound = new Random().nextInt(sizeRandomFactor)
                + sizeConstantFactor;
        this.buildCenterAtOrigin();
    }

    protected Maze(int sizeUpperBound) {
        if (sizeUpperBound <= 0) sizeUpperBound = 0;

        this.mazeId = MazeFactory.newMazeId(this);
        this.sizeUpperBound = sizeUpperBound;
        if (sizeUpperBound > 0) {
            this.buildCenterAtOrigin();
        }
        else print("can't construct a maze of size 0.");
    }

    protected Maze(HashSet<Coordinate> openSet, int sizeUpperBound,
            Coordinate center) {
        if (sizeUpperBound < openSet.size()) sizeUpperBound = openSet.size();
        this.mazeId = MazeFactory.newMazeId(this);
        this.sizeUpperBound = sizeUpperBound;
        this.openSet = openSet;
        if (!this.setCenter(center)) {
            throw new IllegalArgumentException("MAZE CONSTRUCTOR: INVALID CENTER");
        }
    }

    public int sizeUpperBound() { return sizeUpperBound; }

    public boolean newUpperBound(int newUpperBound) { //must be greater than existing upper bound
        if (sizeUpperBound < newUpperBound && resizable) {
            sizeUpperBound = newUpperBound;
          return true;
        }
      return false;
    }

    public int size() { return map.size(); }

    public Coordinate exit() { return exit; }

    public Coordinate center() { return center; }

    public HashSet<Coordinate> openSet() { return openSet; }

    public MazeMap map() { return map; }

    public Room getRoom(Coordinate c) { return map.getRoom(c); }

    public View<Coordinate> viewNeighborsOf(Coordinate c) {
      return map.viewNeighborsOf(c);
    }

    public View<Cardinals> viewAvailableMoves(Coordinate c) {
      return map.viewNeighborDirectionsOf(c);
    }

    public View<Coordinate> viewPositions() { return map.viewAllNodes(); }

    public void printMazeCoordinates() {
        print("\nMaze Coordinates: ");
        for (Coordinate c : map.viewAllNodes()) print(c);
    }

    public void printMazeCoordinatesAndLinks() {
        print("\n Maze Coordinates and Links: ");
        for (Coordinate c : map.viewAllNodes()) print(c + " -> " + map.viewNeighborsOf(c));
    }

    public void printMazeContents() {
        log("\nMaze Contents: ");
        for (Room r : map.viewAllRooms()) {
            log(r + " -> " + r.interactionMap.values());
            //r.describeRoom();
            /* check for uniqueness
            for (Actor i : r.contents) {
                print(i.hashCode());
            }
            */
         }
    }

    public boolean setCenter(Coordinate c) {
        if (openSet.contains(c) || map.contains(c)) {
            center = c;
          return true;
        }
      return false;
    }

    public boolean setExit(Coordinate c) {
        if (map.contains(c) && !center.equals(c)) {
            exit = c;
          return true;
        }
      return false;
    }

    public void buildRoom(Coordinate c) {
        map.build(new Room(c));
    }

    private boolean findExit() {
        HashSet<Coordinate> exitSet = new HashSet<Coordinate>();

        exitSet = getExitSet(exitFactor);

            //print("Exit set => "); print(exitSet);
        if (exitSet.size() >= 1) {
            Coordinate[] exits = exitSet.toArray(new Coordinate[exitSet.size()]);
            int rand = new Random().nextInt(exitSet.size());
            exit = exits[rand];
          return true;
        }
        else {
            print("No exit found.");
          return false;
        }
    }

    private HashSet<Coordinate> getExitSet(Double distanceFactor) {
    //uses the center as a reference point, throws exception if center is undefined
        if (center == null) throw new IllegalStateException("The exit set can't be determined while the maze center is undefined");

      return getCandidateSet(center, exitFactor, InOut.OUT);
    }

    public HashSet<Coordinate> getCandidateSet(Coordinate refPoint, Double distanceFactor, InOut flag) {
        HashMap<Coordinate,Double> distanceMap = new HashMap<Coordinate,Double>();
        HashSet<Coordinate> coordinateSet = new HashSet<Coordinate>();
        //Coordinate origin = new Coordinate();
        Double maxDistance = new Double(0);

        for (Coordinate c : map.viewLinkedNodes()) {
            Double distance = c.getDistanceTo(refPoint);
            maxDistance = (distance > maxDistance) ? distance : maxDistance;
            distanceMap.put(c, distance);
        }
            //print(maxDistance); print(distanceMap);
        for (Map.Entry<Coordinate,Double> entry : distanceMap.entrySet()) {
          if (flag == InOut.IN) {
            if (entry.getValue() <= maxDistance*distanceFactor)
              coordinateSet.add(entry.getKey());
          }
          else if (flag == InOut.OUT) {
            if (entry.getValue() >= maxDistance*distanceFactor)
              coordinateSet.add(entry.getKey());
          }
        }

        return coordinateSet;
   }

    private boolean buildCenterAtOrigin() {
        boolean built = false;
        print("Building center position...");
        if (center == null) {
          center = new Coordinate();
          openSet.add(center);
          map.add(center);
          print("Added the center position at: " + center);
          built = true;
        }
        else {
          print("The center position is already defined!");
          built = false;
        }
      return built;
    }

    //private
    public void populateRooms() {
        PopulateRoom.populify(this);
        //for (Room room : map.viewAllRooms()) {
        //  room.populateRoom();
        // }
    }

    private void setAstarPathToExit() {
        this.path = AStar.discover(this);
    }

    public LinkedList<Coordinate> getAstarPathToExit() {
        return this.path;
    }
}
