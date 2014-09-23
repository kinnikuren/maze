package game.objects.items;

import static game.core.events.Events.equip;
import static game.objects.general.References.*;
import static game.objects.inventory.PaperDoll.EquipSlots.*;
import static game.player.util.Attributes.*;
import static util.Print.*;
import game.core.positional.Coordinate;
import game.objects.inventory.PaperDoll;
import game.objects.inventory.PaperDoll.EquipSlots;
import game.player.util.Attributes;

import java.util.HashMap;

public final class Armor  {

    public static abstract class Helmet extends AbstractItemArmor {
        private Helmet() { super(); }

        @Override
        public EquipSlots type() {
            return HEAD;
        }
    }

    public static class BrownFedora extends Helmet {

        public BrownFedora() {
            super();
            this.name = "Brown Fedora";
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

        @Override
        public EquipSlots type() {
            return CHEST;
        }
    }

    public static class SuperSuit extends ChestArmor {

        public SuperSuit() {
            super();
            this.name = "Super Suit";
            this.stats.put(DEX, 12);
            this.stats.put(STR, 12);
            this.stats.put(INT, 12);
        }

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
