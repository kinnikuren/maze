package maze;

import static maze.Events.equip;
import static maze.References.*;
import static maze.Attributes.*;
import static util.Print.*;
import static maze.PaperDoll.EquipSlots.*;

import java.util.HashMap;

import maze.PaperDoll.EquipSlots;

public final class Armor  {

    public static abstract class Helmet extends AbstractItemArmor {
        private Helmet() { super(); }
        private Helmet(Coordinate c) { super(c); }

        @Override
        public EquipSlots type() {
            return HEAD;
        }
    }

    public static class BrownFedora extends Helmet {
        protected HashMap<Attributes, Integer> stats = new HashMap<Attributes, Integer>();

        public BrownFedora() {
            super();
            this.stats.put(DEX, 1);
        }

        /*
        @Override
        public Event interact(Commands trigger) {
            Event event = super.interact(trigger);
            if (event == null) {
                return trigger == Commands.EQUIP ? equip(this) : null;
            }
            else return event;
        }
        */

        @Override public String name() { return "Brown Fedora"; }

        @Override
        public String details() {
            return "This dusty brown fedora would accessorize well with a bullwhip.";
        }
        @Override
        public int weight() { return 2; }
        @Override
        public boolean matches(String name) {
            return matchRef(FEDORA, name);
        }

        @Override
        public HashMap<Attributes, Integer> getStats() {
            return stats;
        }

    }

    public static abstract class ChestArmor extends AbstractItemArmor {
        private ChestArmor() { super(); }
        private ChestArmor(Coordinate c) { super(c); }

        @Override
        public EquipSlots type() {
            return CHEST;
        }
    }

    public static class SuperSuit extends ChestArmor {
        protected HashMap<Attributes, Integer> stats = new HashMap<Attributes, Integer>();

        public SuperSuit() {
            super();
            this.stats.put(DEX, 100);
            this.stats.put(STR, 100);
            this.stats.put(INT, 100);
        }

        @Override public String name() { return "Super Suit"; }

        @Override
        public String details() {
            return "This will make you super.";
        }
        @Override
        public int weight() { return 5; }
        @Override
        public boolean matches(String name) {
            return matchRef(SUPERSUIT, name);
        }

        @Override
        public HashMap<Attributes, Integer> getStats() {
            return stats;
        }
    }

    //Testing
    public static void main(String[] args) {
        BrownFedora fed = new BrownFedora();

        print(fed.details());
        print(fed.type());

    }
}
