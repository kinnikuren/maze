package tests;

import static util.Print.print;
import static util.Loggers.*;
import static maze.Priority.*;

import maze.BestiaryGenerator;
import maze.Coordinate;
import maze.EerieChimeSound;
import maze.Idol;
import maze.Maze;
import maze.Bestiary.*;
import maze.Consumables.*;
import maze.Useables.*;
import maze.Maze.Room;
import maze.Trinkets.*;
import maze.Weapons.Dagger;

public class PopulateRoomTest {
    static int spawnFactor = 2;

    public static void fillerUp(Coordinate position, Coordinate center, Room room) {
        BestiaryGenerator.spawnMonsters(room);
        if (position.equals(center)) {

            room.addActor(new maze.MysteriousConsole());

            room.addActor(new Idol());
            room.addActor(new HealingPotion());

            room.addActor(new RatKing());

            room.addActor(new Compass());
            room.addActor(new EncNone());

            room.addActor(new BronzeCoin()); room.addActor(new BronzeCoin()); room.addActor(new BronzeCoin());

            //For testing (AYL)
            //addActor(new Skeleton());
            //addActor(new Rat());
            //BestiaryGenerator.spawnMonsters(this);
            Dagger dg = new Dagger();
            room.addActor(dg);
            log("Weapon damage: " + dg.getWeaponDamage(),LOW);

            //testing lots of items
            for (int i = 0; i < spawnFactor; i++) {
                BestiaryGenerator.spawnMonsters(room);
                room.addActor(new BronzeCoin()); room.addActor(new BronzeCoin()); room.addActor(new BronzeCoin());
                room.addActor(new Dagger());
            }


        }
        else {
            //addActor(new Skeleton());
            room.addActor(new BronzeCoin()); room.addActor(new BronzeCoin()); room.addActor(new BronzeCoin());
            //room.addActor(new EerieChimeSound());

        }
    }

}
