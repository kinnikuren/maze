package game.objects.units;

import static util.Print.*;
import static game.core.events.Events.*;
import static game.core.events.Priority.*;
import static game.core.inputs.Commands.*;
import static game.objects.general.References.*;
import static util.Loggers.*;
import game.core.events.Event;
import game.core.events.Events;
import game.core.inputs.Commands;
import game.core.interfaces.Fighter;
import game.core.interfaces.Actor;
import game.core.interfaces.Stage;
import game.core.positional.Coordinate;
import game.objects.general.References;
import game.objects.inventory.Inventory;
import game.objects.items.Trinkets;
import game.objects.items.Trinkets.BronzeCoin;
import game.player.util.Statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Bestiary {

    public static abstract class Monster extends AbstractUnitFighter {
        private Random rand = new Random();
        protected String moveTrigger = null;

        public Monster() {
            super();
            addCoinsToInventory();
        }
        public Monster(Coordinate c) { super(c); }

        private void addCoinsToInventory() {
            int x = rand.nextInt(4);
            for (int i = 0; i < x; i++) {
                this.inventory.add(new Trinkets.BronzeCoin());
            }
        }

        @Override
        public boolean canInteract(AbstractUnit unit) {
          //return unit instanceof Fighter ? true : false;
            return true; //default to true for now
        }
        @Override
        public boolean isDone(Stage stage) {
          return isAlive ? false : true;
        }

        public void buff() { }

        @Override
        public Event interact(Commands trigger) {
            Event event = null;
            if (isAlive) {
              if (trigger == FIGHT) {
                //event = Events.fightAll(monsterParty, HIGH);
                event = Events.fight(this, HIGH);
              } else if (trigger == MOVE) {
                  event = announce(this, DEFAULT, moveTrigger);
              }
              else if (trigger == APPROACH) event = announce(this, LOW, inspect());
            }
          return event;
        }

        @Override
        public Event interact(Commands trigger, String prep, Actor interactee) { return null; }

        @Override
        public Event interact(Commands trigger, String prep, Actor interactee, Stage secondStage) {
            return null;
        }
    }

    public static class Skeleton extends Monster {
        public static final int classId = SKELETON.classId;
        public static final References ref = SKELETON;

        //public static final String battlecry = "The skeleton flashes you a toothy grin as it slowly raises a rusty sword.";
        public Skeleton() {
            super();
            this.moveTrigger = "You hear a rattle of bones";
            statusList.add("undead");
        }

        public Skeleton(Coordinate c) {
            super(c);
            statusList.add("undead");
        }

        private void selfPrint(String input) { print(name + ">> " + input); }

        @Override //overrides AbstractFighterUnit method
        public void defineTypeDefaultValues() {
            this.defaultHitPoints = 8;
            this.defaultAttackVal = 1;
            this.defaultDefenseVal = 0;
            this.damageLow = 2;
            this.damageHigh = 4;
        }
        @Override //overrides AbstractFighterUnit method
        public String battlecry() {
          return "The skeleton flashes you a toothy grin as it slowly raises a rusty sword.";
        }
        @Override
        public String inspect() {
          return ("This reanimated skeleton is poorly constructed.  You are confident you could "
                  + "defeat it in battle.");
        }
        @Override //overrides Unit method
        public void death() {
            if (isAlive) {
              selfPrint("crrk...crrrrk.... ");
              print("The skeleton crumbles before you.");
            }
            super.death();
        }
        //begin implementation of Actor methods defined as abstract in FighterUnit
        @Override
        public boolean matches(String name) {
          return matchRef(SKELETON, name);
        }

    }

    public static class Goblin extends Monster {
        public static final int classId = GOBLIN.classId;
        public static final References ref = GOBLIN;

        public Goblin() {
            super();
            this.damageLow = 3;
            this.damageHigh = 6;
        }

        private void selfPrint(String input) { print(name + ">> " + input); }

        public String message;

        public void setMessage(String message){
            this.message  = message;
        }

        public void getMessage(){
           System.out.println("Your Message : " + message);
        }

        @Override //overrides AbstractFighterUnit method
        public String battlecry() {
          return "The goblin says, 'Hello.'";
        }

        @Override
        public String inspect() { return ("It's a tiny orc."); }

        @Override //overrides Unit method
        public void death() {
            if (isAlive) {
              selfPrint("Aiyee!");
              print("The goblin is dead.");
            }
            super.death();
        }
        //begin implementation of Actor methods defined as abstract in FighterUnit
        @Override
        public boolean matches(String name) { return matchRef(GOBLIN, name); }
    }

    public static class Revanton extends Monster {
        public static final int classId = REVANTON.classId;
        public static final References ref = REVANTON;
        //public static final String battlecry = "The skeleton flashes you a toothy grin as it slowly raises a rusty sword.";

        public Revanton() {
            super();
            this.moveTrigger = "You see something moving in the shadows.";
        }

        public Revanton(Coordinate c) {
            super(c);
        }

        private void selfPrint(String input) { print(name + ">> " + input); }

        @Override //overrides AbstractFighterUnit method
        public void defineTypeDefaultValues() {
            this.defaultHitPoints = 60;
            this.defaultAttackVal = 8;
            this.defaultDefenseVal = 8;
            this.damageLow = 8;
            this.damageHigh = 12;
        }
        @Override //overrides AbstractFighterUnit method
        public String battlecry() {
          return "The elf rogue stealthily waddles toward you.";
        }

        @Override
        public String inspect() {
          return ("All you see are shadows.");
        }

        @Override //overrides Unit method
        public void death() {
            if (isAlive) {
              selfPrint("Revanton will return!");
              print("You spit on the elf's corpse.");
            }
            super.death();
        }
        //begin implementation of Actor methods defined as abstract in FighterUnit
        @Override
        public boolean matches(String name) {
          return matchRef(REVANTON, name);
        }
    }

    public static class Rat extends Monster {
        public static final int classId = RAT.classId;

        public Rat() {
            super();
            statusList.add("beast");
            this.moveTrigger = "You hear squeaking of a non-mechanical nature.";
        }

        public Rat(Coordinate c) {
            super(c);
            statusList.add("beast");
        }

        private void selfPrint(String input) { print(name + ">> " + input); }

        @Override //overrides AbstractFighterUnit method
        public void defineTypeDefaultValues() {
            super.defineTypeDefaultValues();
            this.defaultHitPoints = 4;
            this.defaultAttackVal = 1;
            this.defaultDefenseVal = 0;
            this.damageLow = 1;
            this.damageHigh = 2;
        }
        @Override //overrides AbstractFighterUnit method
        public String battlecry() {
          return "The rat squeaks in alarm.";
        }

        @Override
        public String inspect() {
          return ("It's one of those rats you first encounter when you're just starting out in a dungeon.");
        }

        @Override //overrides Unit method
        public void death() {
            if (isAlive) {
              selfPrint("The rat king will have his revenge!");
              print("The tiny rat collapses.  You feel bad.");
            }
            super.death();
        }
        //begin implementation of Actor methods defined as abstract in FighterUnit
        @Override
        public boolean matches(String name) {
          return matchRef(RAT, name);
        }

    }

    public static class RabidRat extends Rat {
        public static final int classId = RABIDRAT.classId;

        public RabidRat() {
            super();
            this.name = "Rabid Rat";
        }

        public RabidRat(Coordinate c) {
            super(c);
            this.name = "Rabid Rat";
        }

        @Override //overrides AbstractFighterUnit method
        public void defineTypeDefaultValues() {
            super.defineTypeDefaultValues();
            this.defaultAttackVal = 2;
            this.statusEffect = "rabies";
            this.damageLow = 1;
            this.damageHigh = 3;
        }
        @Override //overrides AbstractFighterUnit method
        public String battlecry() {
          return "The rabid rat squeaks in frothy alarm.";
        }

        @Override
        public String inspect() {
          return ("The rat is foaming at the mouth.");
        }
        //begin implementation of Actor methods defined as abstract in FighterUnit
        @Override
        public boolean matches(String name) {
          return matchRef(RABIDRAT, name);
        }
    }

    public static class RatKing extends Rat {
        public static final int classId = RABIDRAT.classId;
        private int ratBuff;

        public RatKing() { //sets values to default
            super();
            this.name = "Rat King";
            this.moveTrigger = "You hear a chorus of "
                    + "squeaking, spanning a spectrum from bass to soprano.";
            this.ratBuff = 0; //initially 0
        }

        public RatKing(Coordinate c) {
            super(c);
            this.name = "Rat King";
        }

        @Override
        public void buff() { //buffs stats based on # rats dead
            int x = 2 * Statistics.globalGet("ratKillCount"); //2 * # of rats killed
            if (x > this.ratBuff) { //if greater than previous buff amount
                this.ratBuff = x;

                this.resetAttack();
                this.resetDefense();
                this.resetHP();

                this.attackVal = this.defaultAttackVal + this.ratBuff;
                this.defenseVal = this.defaultDefenseVal + this.ratBuff;
                this.hitPoints = this.defaultHitPoints + this.ratBuff;

                this.defineTypeDefaultValues();
                this.damageLow += this.ratBuff;
                this.damageHigh += this.ratBuff;
            }
        }

        @Override //overrides AbstractFighterUnit method
        public void defineTypeDefaultValues() {
            super.defineTypeDefaultValues();
            this.defaultAttackVal = 10;
            this.defaultHitPoints = 10;
            this.damageLow = 8;
            this.damageHigh = 10;
        }
        @Override //overrides AbstractFighterUnit method
        public String battlecry() {
          return ("A multitude of rat voices squeak in unison. \nSomehow, the combined cries "
                    + "of the rats is able to form a voice understandable to you.\n"
                    + "'You have killed " + Statistics.globalGet("ratKillCount") + " of our "
                    + "brethren. Prepare to receive your comeuppance!'");
        }

        @Override
        public String inspect() {
          return ("A disgusting mass of rats, both alive and dead, writhes in front of you. The "
                    + "shape constantly changes but there is a uniformity in movement that makes "
                    + "you believe that the thing acts as one unit.");
        }

        @Override //overrides Unit method
        public void death() {
            if (isAlive) {
              print("Countless rat corpses lie at your feet.");
              print("You have earned the title, 'The Exterminator.'");
            }
            this.isAlive = false;
        }
        //begin implementation of Actor methods defined as abstract in FighterUnit
        @Override
        public boolean matches(String name) {
          return matchRef(RATKING, name);
        }
    }
}
