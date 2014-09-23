package game.core.positional;

import java.util.HashSet;
import java.util.Set;

import util.*;

import com.google.common.base.Predicate;

public class Node extends Coordinate {
    public enum Filter implements Predicate<Node> {
      //allows for view filtering on set of nodes for listed conditions
      HAS_NO_LINKS {
        @Override public boolean apply(Node node) {
          return node.numberOfLinks() == 0 ? true : false;
        }
      },
      HAS_TWO_LINKS {
          @Override public boolean apply(Node node) {
              return node.numberOfLinks() == 2 ? true : false;
          }
      },
      HAS_LINKS {
        @Override public boolean apply(Node node) {
          return node.numberOfLinks() > 0 ? true : false;
        };
      }
    }

    //linked positions of this node
    private HashSet<Coordinate> neighborNodes = new HashSet<Coordinate>();

    //inherited constructors
    public Node() {
        super();
    }
    public Node(int x, int y) {
        super(x, y);
    }
    public Node(Coordinate c, Cardinals direction) {
        super(c, direction);
    }
    public Node(Coordinate c, Cardinals direction, int xDistance, int yDistance) {
        super(c, direction, xDistance, yDistance);
    }
    public Node(Coordinate c) {
        super(c.x(), c.y());
    }
    //new methods for linking and viewing
    public int numberOfLinks() { return neighborNodes.size(); }

    public boolean isLinkedTo(Coordinate target) {
        return neighborNodes.contains(target);
    }

    public boolean addLinkTo(Coordinate target) {
    //returns false if the target is already linked OR if a linked node already exists in that direction
        boolean newLink = true;
        if (target == null) newLink = false; //null target also returns false
        else if (this.equals(target)) newLink = false;
        else {
            Cardinals input = Cardinals.get(this, target);
            for (Coordinate neighbor : neighborNodes) {
                Cardinals existing = Cardinals.get(this, neighbor);
                if (input == existing) newLink = false;
                break;
            }
        }
        if (newLink) newLink = neighborNodes.add(target); //if the above checks didn't fail
      return newLink;
    }

    public boolean removeLinkTo(Coordinate neighbor) { return neighborNodes.remove(neighbor); }

    public Set<Coordinate> clearAllLinks() {
        Set<Coordinate> removedLinks = new HashSet<Coordinate>();
        for (Coordinate n : neighborNodes) {
            if (removeLinkTo(n)) removedLinks.add(n);
        }
      return removedLinks;
    }

    public View<Coordinate> viewNeighbors() {
      return new View<Coordinate>(neighborNodes);
    }

    public View<Cardinals> viewNeighborDirections() {
      return new View<Cardinals>(this, viewNeighbors(), Coordinates.Find.DIRECTION);
    }
}
