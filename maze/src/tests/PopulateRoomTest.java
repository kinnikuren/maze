package tests;

import static util.Print.print;
import maze.BestiaryGenerator;
import maze.Coordinate;
import maze.EerieChimeSound;
import maze.Idol;
import maze.Maze;
import maze.Bestiary.RabidRat;
import maze.Consumables.HealingPotion;
import maze.Maze.Room;
import maze.Trinkets.BronzeCoin;
import maze.Weapons.Dagger;

public class PopulateRoomTest {
    static int spawnFactor = 2;

    public static void fillerUp(Coordinate position, Coordinate center, Room room) {
        BestiaryGenerator.spawnMonsters(room);
        if (position.equals(center)) {

            room.addActor(new Idol());
            room.addActor(new HealingPotion());
            room.addActor(new RabidRat());

            room.addActor(new BronzeCoin()); room.addActor(new BronzeCoin()); room.addActor(new BronzeCoin());

            //For testing (AYL)
            //addActor(new Skeleton());
            //addActor(new Rat());
            //BestiaryGenerator.spawnMonsters(this);
            Dagger dg = new Dagger();
            room.addActor(dg);
            print("Weapon damage: " + dg.getWeaponDamage());

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
