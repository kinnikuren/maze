package game.objects.items;

import static game.objects.general.References.*;
import static util.Print.*;
import game.core.positional.Coordinate;

import java.util.LinkedList;

import util.View;

public final class Trinkets {
    private Trinkets() {} //no instantiation
    //contains various trinket classes that extend AbstractItemTrinket
    public static abstract class Coin extends AbstractItemTrinket {
        public Coin() { }
        @Override public abstract boolean matches(String name);
        @Override public abstract String details();
        @Override public abstract int weight();
    }

    public static class BronzeCoin extends Coin {
        public BronzeCoin() { }
        @Override public String name() { return "Bronze Coin"; }
        @Override public boolean matches(String name) { return matchRef(BRONZE_COIN, name); }
        @Override public String details() { return "This bronze coin is very light and faded "
                + "with age."; }
        @Override public int weight() { return 2; }
    }

    public static class SilverCoin extends Coin {
        public SilverCoin() { }
        @Override public String name() { return "Silver Coin"; }
        @Override public boolean matches(String name) { return matchRef(SILVER_COIN, name); }
        @Override public String details() { return "This silver coin is light and still somewhat "
                + "shiny."; }
        @Override public int weight() { return 5; }
    }

    public static class GoldCoin extends Coin {
        public GoldCoin() { }
        @Override public String name() { return "Gold Coin"; }
        @Override public boolean matches(String name) { return matchRef(GOLD_COIN, name); }
        @Override public String details() { return "This gold coin is heavy and has a dull "
                + "lustre."; }
        @Override public int weight() { return 8; }
    }

    public static class GoldenStatue extends AbstractItemTrinket {
        public GoldenStatue() { }
        @Override public String name() { return "Golden Statue"; }
        @Override public boolean matches(String name) { return matchRef(GOLDEN_STATUE, name); }
        @Override public String details() { return "This is going to be worth a lot of money."; }
        @Override public int weight() { return 16; }
    }

    public static class OilyRag extends AbstractItemTrinket {
        public OilyRag() { }
        @Override public String name() { return "Oily Rag"; }
        @Override public boolean matches(String name) { return matchRef(OILY_RAG, name); }
        @Override public String details() { return "This dirty rag smells of kerosene."; }
        @Override public int weight() { return 1; }
    }

    public static class EncNone extends AbstractItemTrinket {
        public EncNone() { }
        @Override public String name() { return "Enc-None"; }
        @Override public boolean matches(String name) { return matchRef(ENCNONE, name); }
        @Override public String details() { return "Tired of pesky random encounters? Keep this in "
                + "your inventory and let Enc-None shield you in a cloak of cowardice!"; }
        @Override public int weight() { return 16; }
    }

}
