package maze;

import java.util.*;
import static util.Utilities.checkNullArg;
import static util.Print.*;

public abstract class AbstractUnit {
    //required members
    Coordinate location;
    int defaultHitPoints;
    int hitPoints;
    boolean isAlive;
    //optional members
    String name; //populated via getClass by default
    HashSet<String> statusList = new HashSet<String>();

    public AbstractUnit() {
        this.name = this.getClass().getSimpleName();
        this.location = null;
        this.isAlive = true;
        this.defineTypeDefaultValues();
        this.resetHP();
    }

    public AbstractUnit(Coordinate c) {
        checkNullArg(c);
        this.name = this.getClass().getSimpleName();
        this.location = c;
        this.isAlive = true;
        this.defineTypeDefaultValues();
        this.resetHP();
    }

    public Coordinate location() { return location; }

    public String name() { return name; }

    public void setLocation(Coordinate c) {
        this.location = c;
    }

    public abstract void defineTypeDefaultValues();

    public int hp() { return hitPoints; }

    public int getDefaultHP() { return this.defaultHitPoints; }

    public void resetHP() { this.hitPoints = this.defaultHitPoints; }

    public void setHP(int hp) {
        this.hitPoints = hp;
        if (hp <= 0 && this.isAlive) { this.death(); }
    }

    public void addHP(int hp) {
        int newHP = this.hitPoints + hp;
        this.setHP(newHP);
    }

    public void loseHP(int hp) {
        int newHP = this.hitPoints - hp;
        this.setHP(newHP);
    }

    public void death() { this.isAlive = false; }

    public boolean isAlive() { return this.isAlive; }

    public void setName(String newName) { this.name = newName; }

    public void printLocation() { print("Current Position: " + location); }
    @Override
    public String toString() { return this.name(); }
}
