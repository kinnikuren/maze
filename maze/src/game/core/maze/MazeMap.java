package game.core.maze;

import static game.core.events.Events.*;
import static game.core.events.Priority.*;
import static game.core.inputs.Commands.*;
import static util.Print.print;
import static util.Utilities.*;
import static util.Loggers.*;
import game.core.events.Event;
import game.core.events.Events;
import game.core.events.Interacter;
import game.core.events.Stage;
import game.core.inputs.Commands;
import game.core.maze.Maze.Room;
import game.core.positional.Cardinals;
import game.core.positional.Coordinate;
import game.core.positional.Coordinates;
import game.core.positional.Node;
import game.core.positional.Node.Filter;
import static game.objects.general.References.*;
import game.objects.general.References;
import game.objects.items.Trinkets;
import game.objects.items.Trinkets.Key;
import game.objects.units.AbstractUnit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import util.NullArgumentException;
import util.View;

import com.google.common.collect.Sets;

public class MazeMap {
    private Map<Node,Room> roomMap = new HashMap<Node,Room>();
    private Set<Gate> gates = new HashSet<Gate>();
    private static int keyCounter = 0;

    public MazeMap() {
        //placeholder constructor
    }

    public boolean contains(Coordinate c) {
      return roomMap.keySet().contains(c);
    }

    public boolean containsAll(Coordinate... coordinates) {
        for (Coordinate c : coordinates) {
          if (!contains(c)) return false;
        }
      return true;
    }

    public boolean add(Coordinate c) throws NullArgumentException {
        checkNullArg(c);
        if (!contains(c)) {
            Node node = new Node(c);
            roomMap.put(node, null);
          return true;
        }
      return false;
    }

    public boolean addPositionSet(HashSet<Coordinate> inputSet) throws NullArgumentException {
        //returns false if couldn't add all the positions to the map, but adds as many as it can
        boolean addResult = true;
        for (Coordinate c : inputSet) {
          if (!add(c)) addResult = false;
        }
      return addResult;
    }

    private Node getNode(Coordinate c) {
        for (Node node : roomMap.keySet()) {
          if (node.equals(c)) return node;
        }
      return null;
    }

    private boolean addLinkTo(Coordinate c, Coordinate target) {
        //we want to update the actual Node object in the keySet -- not create a new Node object
        //returns false if c can't be found (node == null) or if link already exists in that direction
        Node node = getNode(c);
      return node == null ? false : node.addLinkTo(target);
    }

    private boolean removeLinkTo(Coordinate c, Coordinate target) {
        //we want to update the actual Node object in the keySet -- not create a new Node object
        //returns false if c can't be found (node == null) or if link can't be found and removed
        Node node = getNode(c);
      return node == null ? false : node.removeLinkTo(target);
    }

    private void checkLegalArgs(Coordinate... args) throws IllegalArgumentException {
        for (Coordinate c : args) {
          checkNullArg(c);
          if (!contains(c)) {
              throw new IllegalArgumentException("Only coordinates that are present in "
                    + "the map may be used with this method.");
            }
          }
    }

    public boolean link(Coordinate c, Coordinate target) throws IllegalArgumentException {
        //links nodes that already exist in the map; returns false if a link already exists in that direction
        checkLegalArgs(c, target);
      return addLinkTo(c, target);
    }

    public boolean linkDouble(Coordinate c1, Coordinate c2) throws IllegalArgumentException {
        //returns false if either link cannot be added, else it will add both links
        boolean newLinks = false;
        checkLegalArgs(c1, c2);
        if (addLinkTo(c1, c2) && addLinkTo(c2, c1)) newLinks = true;
        else {
            removeLinkTo(c1, c2);
            removeLinkTo(c2, c1);
            newLinks = false;
        }
      return newLinks;
    }

    public boolean addNodesLinkedOneWay(Coordinate c, Coordinate target)
            throws IllegalArgumentException {
        checkNullArgs(c, target);
        add(c); //in case it doesn't exist in the map yet
        add(target); //in case it doesn't exist in the map yet
      return addLinkTo(c, target);
    }

    public boolean addNodesLinkedDouble(Coordinate c1, Coordinate c2) {
        boolean newLinks = false;
        checkNullArgs(c1, c2);
        add(c1); //in case it doesn't exist in the map yet
        add(c2); //in case it doesn't exist in the map yet
        if (addLinkTo(c1, c2) && addLinkTo(c2, c1))  newLinks = true;
        else {
            removeLinkTo(c1, c2);
            removeLinkTo(c2, c1);
            newLinks = false;
        }
      return newLinks;
    }

    public boolean deleteLink(Coordinate c, Coordinate target) throws IllegalArgumentException {
        checkLegalArgs(c, target);
      return removeLinkTo(c, target);
    }

    public boolean deleteLinkDouble(Coordinate c1, Coordinate c2) throws IllegalArgumentException {
        //returns false if either link cannot be deleted, else it will delete both links
        boolean removeLinks = false;
        checkLegalArgs(c1, c2);
        if (removeLinkTo(c1, c2) && removeLinkTo(c2, c1)) removeLinks = true;
        else {
            addLinkTo(c1, c2);
            addLinkTo(c2, c1);
            removeLinks = false;
        }
      return removeLinks;
    }

    public boolean purge(Coordinate c) {
        //deletes node and all links to that node
        boolean purged = false;
        if (roomMap.containsKey(c)) {
            purged = true;
            roomMap.remove(c);
            for (Node node : roomMap.keySet()) {
                node.removeLinkTo(c);
            }
        }
      return purged;
    }

    public Room getRoom(Coordinate c) {
      return roomMap.get(c);
    }

    public Set<Gate> getGates() {
        return gates;
    }

    public void build(Room room) {
        checkNullArg(room);
        Node node = getNode(room.position);
        if (node != null) roomMap.put(node, room);
    }

    public View<Coordinate> viewNeighborsOf(Coordinate c) {
        for (Node node : roomMap.keySet()) {
            if (node.equals(c)) return node.viewNeighbors();
        } //else if key not found
      return null;
    }

    public View<Cardinals> viewNeighborDirectionsOf(Coordinate c) {
        for (Node node : roomMap.keySet()) {
            if (node.equals(c)) {
              return node.viewNeighborDirections();
            }
        } //else if key not found
      return null;
    }

    public View<Coordinate> viewLinkedNodes() {
        Set<Node> linkedNodes = Sets.filter(roomMap.keySet(), Node.Filter.HAS_LINKS);
      return new View<Coordinate>(linkedNodes);
    }

    public View<Coordinate> viewUnlinkedNodes() {
        Set<Node> unlinkedNodes = Sets.filter(roomMap.keySet(), Node.Filter.HAS_NO_LINKS);
      return new View<Coordinate>(unlinkedNodes);
    }

    public View<Coordinate> viewAllNodes() {
      return new View<Coordinate>(roomMap.keySet());
    }

    public View<Room> viewAllRooms() {
      return new View<Room>(roomMap.values());
    }

    public View<Entry<Coordinate, Room>> view() {
        //Map<Coordinate, Room> viewMap = roomMap;
      //return new View<Entry<Node, Room>>(roomMap.entrySet());
      return new View<Entry<Coordinate, Room>>(roomMap);
    }

    public HashSet<Coordinate> getCopyNeighborsOf(Coordinate c) {
        HashSet<Coordinate> copyNeighborSet = new HashSet<Coordinate>();
        for (Coordinate neighbor : viewNeighborsOf(c)) {
            copyNeighborSet.add(neighbor);
        }
      return copyNeighborSet;
    }

    public HashSet<Coordinate> getCopyLinkedNodes() {
        HashSet<Coordinate> copyLinkedSet = new HashSet<Coordinate>();
        Set<Node> linkedNodes = Sets.filter(roomMap.keySet(), Node.Filter.HAS_LINKS);
        for (Coordinate c : linkedNodes) {
            copyLinkedSet.add(c);
        }
      return copyLinkedSet;
    }

    public int size() { return roomMap.keySet().size(); }

    public void deriveStandardLinks() {
        for (Coordinate c : roomMap.keySet()) {
          //print("checking standard links for " + c); //debug
          Coordinate[] primeNeighbors = c.getNeighboringPrimes();
          for (Coordinate prime : primeNeighbors) {
            if (contains(prime)) {
              //print("adding link from " + c + " to " + prime); //debug
              try {
                  this.link(c, prime);
              } catch (IllegalArgumentException iae) { iae.printStackTrace(); }
            }
          }
        }
    }

    public Key addLockedGate(Coordinate c1, Coordinate c2) throws IllegalArgumentException {
        checkLegalArgs(c1, c2);
        Key key = null;
        Room r1 = getRoom(c1);
        Room r2 = getRoom(c2);
        if (anyNullObjects(r1, r2)) return key;

        Gate gate = new Gate(c1, c2);

        if (!gates.contains(gate)) {
          if (gate.lock()) {
            //key = new Key("pseudokey" + ++keyCounter);
            //gate.key = key;
            gates.add(gate);
            r1.addActor(gate);
            r2.addActor(gate);
            return gate.key;
          }
        }
      return null;
    }

    public boolean unlockGate(Coordinate c1, Coordinate c2, Key key) {
        for (Gate g : gates) {
          if (g.matches(c1, c2)) {
            return g.unlock(key);
          }
        }
      return false;
    }

    public Key addLockedGate(Gate gate) throws IllegalArgumentException {
        Coordinate c1 = gate.g.c1;
        Coordinate c2 = gate.g.c2;

        checkLegalArgs(c1, c2);
        Key key = null;
        Room r1 = getRoom(c1);
        Room r2 = getRoom(c2);
        if (anyNullObjects(r1, r2)) return key;

        if (!gates.contains(gate)) {
          if (gate.lock()) {
              log("Adding locked gate to " + gate.g.c1 + " and " + gate.g.c2 + "...");
            gates.add(gate);
            r1.addActor(gate);
            r2.addActor(gate);
            return gate.key;
          }
        }
      return null;
    }

    public class Gate implements Interacter {
        protected Coordinates.Paired g;
        boolean locked = false;
        Key key;
        int maxSpawn = 1;
        String rarity = "rare";
        String name;
        String desc;
        Coordinate diff;
        public final int classId = GATE.classId;
        public final References ref = GATE;

        public Gate() {
            g = null;
        }

        public Gate(Coordinate c1, Coordinate c2) {
            g = new Coordinates.Paired(c1, c2);
            this.key = new Trinkets.PlainKey();
            diff = Coordinates.diff(this.g.c1, this.g.c2);
            this.name = "Locked Gate";
            this.desc = "This locked gate bars your path to the " + Cardinals.get(diff) + ".";
        }
        private Gate(Coordinate c1, Coordinate c2, Key key) {
            g = new Coordinates.Paired(c1, c2);
            this.key = key;
        }
        /* private void setKey(Key key) {
            this.key = key;
        } */

        public Key getKey() {
            return key;
        }

        @Override
        public String toString() {
            return name;
        }

        public boolean isLocked() {
            boolean lock1 = !MazeMap.this.viewNeighborsOf(g.c1).contains(g.c2);
            boolean lock2 = !MazeMap.this.viewNeighborsOf(g.c2).contains(g.c1);
          return lock1 && lock2;
        }
        public boolean lock() {
          return MazeMap.this.deleteLinkDouble(g.c1, g.c2);
        }
        public boolean unlock(Key key) {
            boolean result = false;
            if (this.key == null) {
                result = false;
            } else if (!this.key.equals(key)) {
                result = false;
            } else {
                result = MazeMap.this.linkDouble(g.c1, g.c2);
                if (result) {
                    this.name = "Unlocked Gate";
                    setDescription();
                }
            }
            return result;
        }
        public boolean matches(Coordinate c1, Coordinate c2) {
          return this.g.matches(c1, c2);
        }

        public void setDescription() {
            this.desc = "This gate is now unlocked and the path " + Cardinals.get(diff) + " is clear.";
        }

        public String inspect() {
            return desc;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;

            if (o instanceof Gate) {
              Gate g = (Gate)o;
              if (this.g.matches(g.g))
                return true;
            }
          return false;
        }
        @Override
        public int hashCode() { return g.hashCode(); }

        @Override
        public String name() { return name; }
        @Override
        public boolean matches(String name) {
          return matchRef(GATE, name);
        }
        @Override
        public boolean canInteract(AbstractUnit unit) {
          return true; //instanceof key, after changing interacter to object
        }
        @Override
        public Event interact(Commands trigger) {
          //return isLocked() && trigger == WHEREGO ? announce(this, LOW, "You see a locked door.") : null;
            Event event = null;
            if (trigger == DESCRIBE)
                event = Events.describe(this);
            return event;
        }

        @Override
        public Event interact(Commands trigger, String prep, Interacter interactee) { return null; }

        @Override
        public Event interact(Commands trigger, String prep, Interacter interactee, Stage secondStage) {
            return null;
        }

        @Override
        public boolean isDone(Stage stage) { return false; }

        @Override
        public int getMaxSpawn() {
            return maxSpawn;
        }

        @Override
        public void setMaxSpawn(int maxSpawn) {
            this.maxSpawn = maxSpawn;
        }
        @Override
        public String getRarity() { return rarity; }
        @Override
        public void setRarity(String rarity) { this.rarity = rarity; }
    }

    public class RedDoor extends Gate {
        public final int classId = RED_DOOR.classId;
        public final References ref = RED_DOOR;

        public RedDoor() {
            g = null;
        }

        public RedDoor(Coordinate c1, Coordinate c2) {
            g = new Coordinates.Paired(c1, c2);
            this.key = new Trinkets.RedKey();
            diff = Coordinates.diff(this.g.c1, this.g.c2);
            this.name = "Locked Red Door";
            this.desc = "This red door bars your path to the " + Cardinals.get(diff) + ".";
        }

        @Override
        public boolean matches(String name) {
          return matchRef(RED_DOOR, name);
        }
    }
}
