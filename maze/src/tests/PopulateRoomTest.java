package tests;

import static util.Print.print;
import static util.Loggers.*;
import static game.core.events.Priority.*;
import game.content.alerts.EerieChimeSound;
import game.core.maze.Maze;
import game.core.maze.Maze.Room;
import game.core.positional.Coordinate;
import game.objects.general.BestiaryGenerator;
import game.objects.items.Idol;
import game.objects.items.Armor.*;
import game.objects.items.Consumables.*;
import game.objects.items.Trinkets.*;
import game.objects.items.Useables.*;
import game.objects.items.Weapons.*;
import game.objects.units.Bestiary.*;

public class PopulateRoomTest {
    static int spawnFactor = 2;

    public static void fillerUp(Coordinate position, Coordinate center, Room room) {
        BestiaryGenerator.spawnMonsters(room);
        if (position.equals(center)) {

            room.addActor(new game.objects.items.MysteriousConsole());

            room.addActor(new Idol());
            room.addActor(new HealingPotion());

            room.addActor(new RatKing());

            room.addActor(new Compass());
            room.addActor(new WarpWhistle());
            room.addActor(new EncNone());
            room.addActor(new BrownFedora());
            room.addActor(new SuperSuit());

            room.addActor(new BronzeCoin()); room.addActor(new BronzeCoin()); room.addActor(new BronzeCoin());
            room.addActor(new GoldCoin());
            room.addActor(new GoldCoin());
            room.addActor(new GoldCoin());

            //For testing (AYL)
            //addActor(new Skeleton());
            //addActor(new Rat());
            //BestiaryGenerator.spawnMonsters(this);
            Dagger dg = new Dagger();
            room.addActor(dg);
            room.addActor(new Longsword());

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
