package game.objects.items;

import static game.core.inputs.Commands.USE;
import static game.objects.general.References.*;
import static game.objects.inventory.PaperDoll.EquipSlots.*;
import static game.player.util.Attributes.*;
import static util.Print.print;
import game.core.events.Event;
import game.core.events.Events;
import game.core.inputs.Commands;
import game.core.interfaces.Actor;
import game.core.interfaces.Stage;
import game.core.positional.Coordinate;
import game.objects.inventory.PaperDoll.EquipSlots;
import game.objects.obstacles.TheDarkness;
import game.objects.units.AbstractUnit;
import game.objects.units.Player;
import game.player.util.Attributes;

import java.util.HashMap;

public final class Weapons {
    public static class Dagger extends AbstractItemWeapon {
        private String name;
        private util.SuffixGenerator sg = new util.SuffixGenerator();
        private String suffix = sg.getSuffix();
        protected HashMap<Attributes, Integer> stats = new HashMap<Attributes, Integer>();

        public Dagger() {
            super();
            this.name = "Dagger " + suffix;
            this.stats.put(WDLOW, 2);
            this.stats.put(WDHIGH, 4);
        }

        public Dagger(Coordinate c) { super(c); }

        @Override public String name() { return name; }
        @Override public boolean matches(String name) { return matchRef(DAGGER, name); }
        @Override public String details() { return "This dagger's fancy name is irrelevant.  It is "
                + "rusty and in poor condition.  On the plus side, you could probably give someone "
                + "tetanus with this weapon."; }
        @Override public int weight() { return 10; }

        @Override
        public HashMap<Attributes, Integer> getStats() {
            return stats;
        }
    }

    public static class LongSword extends AbstractItemWeapon {
        private String name = "Longsword";
        protected HashMap<Attributes, Integer> stats = new HashMap<Attributes, Integer>();

        public LongSword() {
            super();
            this.stats.put(WDLOW, 3);
            this.stats.put(WDHIGH, 5);
        }

        public LongSword(Coordinate c) { super(c); }

        @Override public String name() { return name; }
        @Override public boolean matches(String name) { return matchRef(LONGSWORD, name); }
        @Override public String details() { return "Longer is better."; }
        @Override public int weight() { return 10; }

        @Override
        public HashMap<Attributes, Integer> getStats() {
            return stats;
        }
    }

    public static class WoodenStick extends AbstractItemWeapon {
        private String name = "Wooden Stick";
        protected HashMap<Attributes, Integer> stats = new HashMap<Attributes, Integer>();

        public WoodenStick() {
            super();
            this.stats.put(WDLOW, 1);
            this.stats.put(WDHIGH, 2);
        }

        @Override public String name() { return name; }
        @Override public boolean matches(String name) { return matchRef(WOODEN_STICK, name); }
        @Override public String details() { return "You chuckle as you think of one of many puns."; }
        @Override public int weight() { return 2; }

        @Override
        public HashMap<Attributes, Integer> getStats() {
            return stats;
        }
    }

    public static class UnlitTorch extends AbstractItemWeapon {
        private String name = "Unlit Torch";
        protected HashMap<Attributes, Integer> stats = new HashMap<Attributes, Integer>();

        public UnlitTorch() {
            super();
            this.stats.put(WDLOW, 1);
            this.stats.put(WDHIGH, 2);
        }

        @Override public String name() { return name; }
        @Override public boolean matches(String name) { return matchRef(UNLIT_TORCH, name); }
        @Override public String details() { return "All you need is fire."; }
        @Override public int weight() { return 2; }

        @Override
        public HashMap<Attributes, Integer> getStats() {
            return stats;
        }
    }

    public static class LitTorch extends AbstractItemWeapon {
        private String name = "Lit Torch";
        protected HashMap<Attributes, Integer> stats = new HashMap<Attributes, Integer>();

        public LitTorch() {
            super();
            this.stats.put(WDLOW, 2);
            this.stats.put(WDHIGH, 3);
        }

        @Override public String name() { return name; }
        @Override public boolean matches(String name) { return matchRef(LIT_TORCH, name); }
        @Override public String details() { return "All you need is a little fire to brighten "
                + "up the day"; }
        @Override public int weight() { return 2; }

        @Override
        public HashMap<Attributes, Integer> getStats() {
            return stats;
        }

        @Override
        public Event interact(Commands trigger, String prep, Actor interactee, Stage secondStage) {
            if (trigger == USE) {
                if (prep.equals("on")) {
                    return Events.useOn(this, interactee, secondStage);
                } else return null;
            } else {
                return null;
            }
        }

        @Override
        public boolean usedBy(Player player, Actor target, Stage targetStage) {
            print("You attempt to use the " + this + " on " + target.name() + ".");
            if (target instanceof TheDarkness) {
                TheDarkness dk = ((TheDarkness)target);
                dk.dispelDarkness(player.maze());
                print("The Darkness is dispelled!\n");
                targetStage.removeActor(target);
                player.getRoom().describeRoom();
                return true;
            } else {
                print("Nothing happens.");
                return false;
            }
        }
    }
}
