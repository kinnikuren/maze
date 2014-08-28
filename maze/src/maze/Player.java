package maze;

import static util.Print.*;
import maze.Maze.Room;
import util.View;

public class Player extends AbstractUnitFighterMover
implements Fighter {
    private Maze maze;
    private Narrator narrator;

    public void setMaze(Maze ref) { maze = ref; }

    public Maze maze() { return maze; }

    public Narrator narrator() { return this.narrator; }

    public Room getRoom() { return maze.getRoom(location); }

    public Player(Maze maze) {
        super(maze.center()); //creates Player at location c
        this.maze = maze;
        this.narrator = new Narrator();
    }

    @Override //overrides AbstractFighterUnit method
    public void defineTypeDefaultValues() {
        super.defineTypeDefaultValues();
        this.defaultHitPoints = 20;
        this.defaultAttackVal = 1;
        this.defaultDefenseVal = 0;
        //this.status = "healthy";
    }
    @Override //overrides AbstractFighterUnit method
    public String battlecry() {
        return "Ahhhh!!!!";
    }
    @Override //overrides Unit method
    public void addHP(int hp) {
        print("You have gained " + hp + " health.");
        super.addHP(hp);
    }
    @Override //overrides Unit method
    public void loseHP(int hp) {
        print("You have taken " + hp + " damage.");
        super.loseHP(hp);
    }
    @Override //overrides Unit method
    public void death() {
        super.death();
        print("You have died.");
    }

    //begin implementation of mover abstract methods
    @Override
    public void whereCanIGo(Maze maze) {
        for (Cardinals d: maze.viewAvailableMoves(location)) {
            print(d);
        }
    }
    @Override
    public boolean move(Cardinals move, Maze maze) {
        boolean didMove = false;
        for (Coordinate neighbor : maze.viewNeighborsOf(location)) {
            Cardinals direction = Cardinals.get(location, neighbor);
            if (direction == move) {
                this.setLocation(neighbor);
                didMove = true;
              break;
            }
        }
      return didMove;
    }
    //begin dummy methods for interacter
    @Override
    public boolean matches(String name) {
      return name().equalsIgnoreCase(name);
    }
    @Override
    public boolean canInteract(AbstractUnit unit) {
      return true; //dummy method
    }
    @Override
    public Event interact(Commands trigger) {
      //dummy method, potential future use for player-to-player interaction
      return null;
    }
    @Override
    public boolean isDone(Stage stage) {
      return false; //dummy method
    }

    /*public void pickUp(Item i) {
        String s = i.getClass().getSimpleName();
        if(!inventory.contains(s)) {
            inventory.put(s,1);
        }
        else {
            inventory.put(s,inventory.get(s)+1);
        }
    }*/
    private Inventory inventory = new Inventory();

    public boolean equip(Equippable item) {
        if (!inventory.contains(item)) {
            print("You do not have " + item + " in your inventory.");
            return false;
        }
        else {
            //inventory.removeActor(item);
            //this.defaultAttackVal = item.getWeaponDamage()[0];
            this.defaultAttackVal += item.getWeaponDamage();
            print("Attack value: " + this.defaultAttackVal);
            return true;
        }
    }

    public void addToInventory(Portable item) { inventory.add(item); }

    public boolean deleteFromInventory(Portable item) {
      return inventory.remove(item) != null ? true : false;
    }

    public Portable dropFromInventory(Portable item) { return inventory.remove(item); }

    public Inventory getInventory() { return inventory; }

    public void printInventory() { print(inventory); }

    public void printStatus() {
        if (status == "healthy") {
            print("You have " + hp() + " hitpoints and are otherwise fine.");
        }
        else {
            print("You have " + hp() + " hitpoints but you are afflicted with " + status + ".");
        }
    }
}
