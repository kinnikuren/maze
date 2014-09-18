package game.objects.general;

import game.core.events.Interacter;
import game.core.maze.AbstractRoom;
import game.core.maze.Maze;

import java.util.*;

import static game.objects.units.Bestiary.*;

public class BestiaryGenerator {
    private static Random rand = new Random();

    public static Monster summonMonster(References ref) {
        switch (ref) {
          case SKELETON: return (new Skeleton());
          case RAT: return (new Rat());
          case RABIDRAT: return (new RabidRat());
          default: return null;
        }
    }

    public static Monster getRandomMonster() {
        //References monsterRef = new References();
        References monsterRef;
        Monster randMonster;

        int randomID;
        do {
            randomID = 100 + rand.nextInt(10);
            //System.out.println(randomID);
            monsterRef = References.get(randomID);
            //System.out.println(monsterRef);
        } while (monsterRef == References.ERROR);

        //System.out.println(monsterRef);
        //System.out.println(sk.classId);
        randMonster = summonMonster(monsterRef);
        //System.out.println(randMonster.name);

      return randMonster;
    }

    public static boolean thereAreMonsters(Maze.Room room) {
        for (Interacter actor : room.interactions()) {
            if (actor instanceof Monster) return true;
        }
      return false;
    }

    public static boolean shouldMonstersSpawn() {
        //Chance of monster spawning
        double spawnChance = 1;

        //Does a monster spawn?
        if ((1 + rand.nextInt(100))*0.01 <= spawnChance) {
            return true;
        }

        return false;
    }

    public static int numberOfMonsters() {
        return (rand.nextInt(4)+1);
    }

    public static void spawnMonsters(AbstractRoom room) {
        if (shouldMonstersSpawn()) {
            for (int i = 0; i <= numberOfMonsters(); i++) {
                room.addActor(getRandomMonster());
            }
        }
    }

    /*
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        getMonster();
    }
    */

}
