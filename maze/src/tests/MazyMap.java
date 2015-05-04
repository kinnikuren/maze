package tests;

import static util.Print.print;
import static util.Utilities.*;
import game.core.maze.Maze.Room;
import game.core.positional.Cardinals;
import game.core.positional.Coordinate;
import game.core.positional.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import util.View;

import com.google.common.collect.Sets;

public class MazyMap {
    private HashMap<Node,Room> roomMap = new HashMap<Node,Room>();
    private HashSet<Gate> gates = new HashSet<Gate>();
    private static int keyCounter = 0;

    public MazyMap() {
        //placeholder constructor
    }

    public boolean add(Coordinate c) {
        checkNullArg(c);
        if (roomMap.keySet().contains(c)) return false;
        else {
            Node node = new Node(c);
            roomMap.put(node, null);
          return true;
        }
    }

    public boolean contains(Coordinate c) {
      return roomMap.keySet().contains(c);
    }

    public boolean containsAll(Coordinate... coordinates) {
        boolean containsAll = true;
        for (Coordinate c : coordinates) {
          if (!contains(c)) containsAll = false;
          break;
        }
      return containsAll;
    }

    public boolean addPositionSet(HashSet<Coordinate> inputSet) {
        //returns false if all positions in the input set already exist
        boolean addResult = false;
        for (Coordinate c : inputSet) {
            if (add(c)) {
                addResult = true;
              break;
            }
        }
      return addResult;
    }

    private boolean addLinkTo(Coordinate c, Coordinate target) {
        boolean newLink = false;
        Node node = getNode(c);
        if (node != null) { //we want to update the actual Node object in the keySet
                            //-- not create a new Node object
            newLink = node.addLinkTo(target); //returns false if link already exists in that direction
        }
      return newLink;
    }

    private boolean removeLinkTo(Coordinate c, Coordinate target) {
        boolean deleteLink = false;
        Node node = getNode(c);
        if (node != null) { //we want to update the actual Node object in the keySet
                            //-- not create a new Node object
            deleteLink = node.removeLinkTo(target); //returns false if link can't be found and removed
        }
      return deleteLink;
    }

    private Node getNode(Coordinate c) {
        for (Node node : roomMap.keySet()) {
            if (node.equals(c)) return node;
        }
      return null;
    }

    private void checkLegalArgs(Coordinate... coordinates) {
        checkNullArg(coordinates);
        if (!containsAll(coordinates)) {
          throw new IllegalArgumentException("Only coordinates that are present in "
                + "the map may be used with this method.");
        }
    }

    public boolean link(Coordinate c, Coordinate target) {
        //links nodes that already exist in the map; returns false if a link already exists in that direction
        checkLegalArgs(c, target);
      return addLinkTo(c, target);
    }

    public boolean linkDouble(Coordinate c1, Coordinate c2) {
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

    public boolean addNodesLinkedOneWay(Coordinate c, Coordinate target) {
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

    public boolean deleteLink(Coordinate c, Coordinate target) {
        checkLegalArgs(c, target);
      return removeLinkTo(c, target);
    }

    public boolean deleteLinkDouble(Coordinate c1, Coordinate c2) {
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

    public View<Coordinate> viewAllNodes() {
      return new View<Coordinate>(roomMap.keySet());
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

    public void printUnlinkedNodes() {
        Set<Node> unlinkedNodes = Sets.filter(roomMap.keySet(), Node.Filter.HAS_NO_LINKS);
        for (Coordinate c : unlinkedNodes) {
            print("*** disconnected position at: " + c);
        }
    }

    public Key addLockedGate(Coordinate c1, Coordinate c2) {
        checkLegalArgs(c1, c2);

        Gate gate = new Gate(c1, c2);
        Key key = null;
        if (gates.add(gate)) {
          if (gate.lock()) {
            key = new Key("pseudokey" + keyCounter++);
            gate.key = key;
          }
        }
      return key;
    }

    public boolean unlockGate(Coordinate c1, Coordinate c2, Key key) {
        for (Gate g : gates) {
          if (g.matches(c1, c2)) {
            return g.unlock(key);
          }
        }
      return false;
    }

    public final class Key {
        final String id;
        private Key(String id) {
            this.id = id;
        }
        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (o == null) return false;

            if (o instanceof Key) {
                Key k = (Key)o;
                if (k.id.equals(id)) return true;
            }
          return false;
        }
        @Override
        public int hashCode() { return id.hashCode(); }
    }

    public class Gate {
        final Coordinate c1;
        final Coordinate c2;
        Key key;
        private Gate(Coordinate c1, Coordinate c2) {
            this.c1 = c1;
            this.c2 = c2;
            this.key = null;
        }
        private Gate(Coordinate c1, Coordinate c2, Key key) {
            this.c1 = c1;
            this.c2 = c2;
            this.key = key;
        }
        /* private void setKey(Key key) {
            this.key = key;
        } */
        private boolean lock() {
            return MazyMap.this.deleteLinkDouble(c1, c2);
        }
        private boolean unlock(Key key) {
            return this.key == null ? false
               : (!this.key.equals(key) ? false
               : MazyMap.this.linkDouble(c1, c2));
        }
        private boolean matches(Coordinate c1, Coordinate c2) {
            return this.c1.equals(c1) && this.c2.equals(c2) ? true
                 : this.c1.equals(c2) && this.c2.equals(c1);
        }
        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (o == null) return false;

            if (o instanceof Gate) {
                Gate g = (Gate)o;
                if (matches(g.c1, g.c2))
                  return true;
            }
          return false;
        }
        @Override
        public int hashCode() {
            int x = c1.hashCode();
            int y = c2.hashCode();
          return ((x*x)+17)*31 + ((y*y)+17)*31;
        }
    }
}
