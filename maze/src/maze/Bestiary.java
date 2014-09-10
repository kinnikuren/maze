package maze;

import static util.Print.*;
import static maze.References.*;
import static maze.Priority.*;
import static maze.Commands.*;
import static maze.Events.*;

public final class Bestiary {
    public static abstract class Monster extends AbstractUnitFighter {
        public Monster() { }
        public Monster(Coordinate c) { super(c); }

        @Override
        public boolean canInteract(AbstractUnit unit) {
          return unit instanceof Fighter ? true : false;
        }
        @Override
        public boolean isDone(Stage stage) {
          return isAlive ? false : true;
        }

        public void buff() { }
    }

    public static class Skeleton extends Monster {
        public static final int classId = SKELETON.classId;
        public static final References ref = SKELETON;
        //public static final String battlecry = "The skeleton flashes you a toothy grin as it slowly raises a rusty sword.";
        public Skeleton() {
            super();
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
        }
        @Override //overrides AbstractFighterUnit method
        public String battlecry() {
          return "The skeleton flashes you a toothy grin as it slowly raises a rusty sword.";
        }

        @Override
        public String inspect() {
            return ("This reanimated skeleton is poorly constructed.  You are confident you could defeat it in battle.");
        }

        @Override //overrides Unit method
        public void death() {
            if (isAlive) {
              selfPrint("crrk...crrrrk.... ");
              print("The skeleton crumbles before you.");
            }
            super.death();
        }
        //begin implementation of Interacter methods defined as abstract in FighterUnit
        @Override
        public boolean matches(String name) {
          return matchRef(SKELETON, name);
        }

        @Override
        public Event interact(Commands trigger) {
            if (!isAlive) return null;

            return trigger == FIGHT ? fight(this, HIGH)
                : (trigger == APPROACH ? announce(this, LOW, inspect())
                : (trigger == MOVE ? announce(this, DEFAULT, "You hear a rattle of bones")
                :  null));
        }
    }

    public static class Rat extends Monster {
        public static final int classId = RAT.classId;

        public Rat() {
            super();
            statusList.add("beast");
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
        //begin implementation of Interacter methods defined as abstract in FighterUnit
        @Override
        public boolean matches(String name) {
          return matchRef(RAT, name);
        }

        @Override
        public Event interact(Commands trigger) {
            if (!isAlive) return null;

            return trigger == FIGHT ? fight(this, HIGH)
                : (trigger == APPROACH ? announce(this, HIGH, inspect())
                : (trigger == MOVE ? announce(this, DEFAULT, "You hear squeaking of a non-mechanical nature.")
                :  null));
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

        private void selfPrint(String input) { print(name + ">> " + input); }

        @Override //overrides AbstractFighterUnit method
        public void defineTypeDefaultValues() {
            super.defineTypeDefaultValues();
            this.defaultAttackVal = 2;
            this.statusEffect = "rabies";
        }
        @Override //overrides AbstractFighterUnit method
        public String battlecry() {
          return "The rabid rat squeaks in frothy alarm.";
        }

        @Override
        public String inspect() {
            return ("The rat is foaming at the mouth.");
        }

        @Override //overrides Unit method
        public void death() {
            if (isAlive) {
              selfPrint("The rat king will have his revenge!");
              print("The tiny rat collapses. You feel bad.");
            }
            super.death();
        }
        //begin implementation of Interacter methods defined as abstract in FighterUnit
        @Override
        public boolean matches(String name) {
          return matchRef(RABIDRAT, name);
        }

        @Override
        public Event interact(Commands trigger) {
            if (!isAlive) return null;

            return trigger == FIGHT ? fight(this, HIGH)
                : (trigger == APPROACH ? announce(this, HIGH, inspect())
                : (trigger == MOVE ? announce(this, DEFAULT, "You hear squeaking of a non-mechanical nature.")
                :  null));
        }
    }

    public static class RatKing extends Rat {
        public static final int classId = RABIDRAT.classId;
        private int ratBuff;

        public RatKing() { //sets values to default
            super();
            this.name = "Rat King";
            this.ratBuff = 2 * Statistics.globalGet("ratKillCount"); //initially 0
        }

        public RatKing(Coordinate c) {
            super(c);
            this.name = "Rat King";
        }

        private void selfPrint(String input) { print(name + ">> " + input); }

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
            }
        }

        @Override //overrides AbstractFighterUnit method
        public void defineTypeDefaultValues() {
            super.defineTypeDefaultValues();
            this.defaultAttackVal = 10;
            this.defaultHitPoints = 10;
        }
        @Override //overrides AbstractFighterUnit method
        public String battlecry() {
          return "A multitude of rat voices squeak in unison.";
        }

        @Override
        public String inspect() {
            return ("A disgusting mass of rats, both alive and dead, writhes in front of you. The "
                    + "shape constantly changes but there is a uniformity in movement that makes "
                    + "you believe that the thing acts as one unit. Somehow, the combined cries "
                    + "of the rats is able to form a voice understandable to you.\n"
                    + "'You have killed " + Statistics.globalGet("ratKillCount") + " of our "
                    + "brethren. Prepare to receive your comeuppance!'");
        }

        @Override //overrides Unit method
        public void death() {
            if (isAlive) {
              print("Countless rat corpses lie at your feet.");
              print("You have earned the title, 'The Exterminator.'");
            }
            super.death();
        }
        //begin implementation of Interacter methods defined as abstract in FighterUnit
        @Override
        public boolean matches(String name) {
          return matchRef(RATKING, name);
        }

        @Override
        public Event interact(Commands trigger) {
            if (!isAlive) return null;

            return trigger == FIGHT ? fight(this, HIGH)
                : (trigger == APPROACH ? announce(this, HIGH, inspect())
                : (trigger == MOVE ? announce(this, DEFAULT, "You hear a chorus of squeaking, "
                        + "spanning a spectrum from bass to soprano.")
                :  null));
        }
    }
}
