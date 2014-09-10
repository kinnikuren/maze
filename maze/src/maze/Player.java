package maze;

import static util.Print.*;
import static util.Loggers.*;
import static maze.References.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import maze.Maze.Room;
import util.GrammarGuy;
import util.View;

public class Player extends AbstractUnitFighterMover
implements Fighter {
    private Maze maze;
    private Narrator narrator;
    private Statistics stats;
    private EncounterTracker tracker;
    private Random rand = new Random();

    private int dexterity;
    private int strength;
    private int intelligence;

    public void setMaze(Maze ref) { maze = ref; }

    public Maze maze() { return maze; }

    public Narrator narrator() { return this.narrator; }

    public Statistics stats() { return this.stats; }

    public EncounterTracker tracker() { return this.tracker; }

    public int getDex() { return this.dexterity; }
    public int getStr() { return this.strength; }
    public int getInt() { return this.intelligence; }

    public Room getRoom() { return maze.getRoom(location); }

    public Player(Maze maze) {
        super(maze.center()); //creates Player at location c
        this.maze = maze;
        this.narrator = new Narrator();
        this.stats = new Statistics();
        this.tracker = new EncounterTracker();
        this.dexterity = 4;
        this.strength = 4;
        this.intelligence = 4;
    }

    public boolean skillCheck(References ref,int dmg, int difficulty) {
        int roll;
        int successes = 0;
        int playerSkill=0;

        switch(ref) {
            case DEX:
                playerSkill = this.dexterity;
                break;
            case STR:
                playerSkill = this.strength;
                break;
            case INT:
                playerSkill = this.intelligence;
                break;
        }

        for (int i=0;i < playerSkill;i++) {
            roll = rand.nextInt(6)+1;
            log("You rolled a " + ((Integer)roll).toString());
            if (roll == 5 || roll == 6) {
                successes++;
            }
        }

        if (successes >= difficulty) {
            return true;
        } else {
            this.loseHP(dmg);
            return false;
        }
    }

    public void skillChange(References ref, int amt) {
        switch(ref) {
        case DEX:
            this.dexterity += amt;
            break;
        case STR:
            this.strength += amt;
            break;
        case INT:
            this.intelligence += amt;
            break;
        }
        if (amt > 0) {
            print("You have gained " + amt + " " + ref.toString() + ".");
        } else if (amt < 0) {
            print("You have lost " + amt + " " + ref.toString() + ".");
        } else {
            print("Your skills remain the same.");
        }
    }

    @Override //overrides AbstractFighterUnit method
    public void defineTypeDefaultValues() {
        super.defineTypeDefaultValues();
        this.defaultHitPoints = 20;
        this.defaultAttackVal = 2;
        this.defaultDefenseVal = 0;
        //this.status = "healthy";
    }
    @Override //overrides AbstractFighterUnit method
    public String battlecry() {
        return "Ahhhh!!!!";
    }

    @Override
    public void setStatus(String status) {
        super.setStatus(status);
        print("You have been afflicted with " + status);
    }


    @Override //overrides Unit method
    public void addHP(int hp) {
        print("You have gained " + hp + " health.");
        super.addHP(hp);
    }
    @Override //overrides Unit method
    public void loseHP(int hp) {
        if (hp > 0) {
            print("You have taken " + hp + " damage.");
            super.loseHP(hp);
        }
    }
    @Override //overrides Unit method
    public void death() {
        super.death();
        print("You have died.");
    }

    //begin implementation of mover abstract methods
    @Override
    public void whereCanIGo(Maze maze) {
        List<String> list = new ArrayList<String>();
        for (Cardinals d: maze.viewAvailableMoves(location)) {
            //printnb(d + " ");
            list.add(d.name());
        }
        print(GrammarGuy.oxfordCommify(list));
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
        String statusList;
        statusList = util.GrammarGuy.oxfordCommify(status);
        if (status.isEmpty()) {
            print("You have " + hp() + " hitpoints and are otherwise fine.");
        }
        else {
            print("You have " + hp() + " hitpoints but you are afflicted with " + statusList + ".");
        }
    }
}
