package game.objects.items;

import static util.Print.*;
import static game.objects.general.References.*;
import static java.lang.Math.*;
import game.core.positional.Coordinate;
import game.objects.units.Player;

import java.util.LinkedList;

public final class Consumables {
    private Consumables() {} //no instantiation
    //contains various consumable classes that extend AbstractItemConsumable
    public static abstract class Potion extends AbstractItemConsumable {
        public Potion() { }
        public Potion(Coordinate c) { super(c); }
        @Override public abstract boolean matches(String name);
        @Override public abstract String details();
        @Override public abstract int weight();
        @Override public abstract void consumedBy(Player player);
    }

    public static class HealingPotion extends Potion {
        private int healing = 4;
        public HealingPotion() { }
        public HealingPotion(Coordinate c) { super(c); }
        @Override public String name() { return "Healing Potion"; }
        @Override public boolean matches(String name) { return matchRef(HEALING_POTION, name); }
        @Override public String details() { return "This looks like a healing potion."; }
        @Override public int weight() { return 5; }
        @Override public void consumedBy(Player player) {
            int healed = min(healing, player.getDefaultHP() - player.hp());
            player.addHP(healed);
            print("Refreshing!\n");
        }
    }

    public static class Apple extends Potion {
        private int healing = 2;
        public Apple() { }
        public Apple(Coordinate c) { super(c); }
        @Override public String name() { return "Apple"; }
        @Override public boolean matches(String name) { return matchRef(APPLE, name); }
        @Override public String details() { return "It's a crisp, delicious apple."; }
        @Override public int weight() { return 5; }
        @Override public void consumedBy(Player player) {
            int healed = min(healing, player.getDefaultHP() - player.hp());
            player.addHP(healed);
            print("Yum!\n");
        }
    }
}
