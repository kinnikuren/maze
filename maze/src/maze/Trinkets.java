package maze;

import static maze.References.*;

public final class Trinkets {
    private Trinkets() {} //no instantiation
    //contains various trinket classes that extend AbstractItemTrinket
    public static abstract class Coin extends AbstractItemTrinket {
        public Coin() { }
        public Coin(Coordinate c) { super(c); }
        @Override public abstract boolean matches(String name);
        @Override public abstract String details();
        @Override public abstract int weight();
    }

    public static class BronzeCoin extends Coin {
        public BronzeCoin() { }
        public BronzeCoin(Coordinate c) { super(c); }
        @Override public String name() { return "Bronze Coin"; }
        @Override public boolean matches(String name) { return matchRef(BRONZE_COIN, name); }
        @Override public String details() { return "This bronze coin is very light and faded with age."; }
        @Override public int weight() { return 2; }
    }

    public static class SilverCoin extends Coin {
        public SilverCoin() { }
        public SilverCoin(Coordinate c) { super(c); }
        @Override public String name() { return "silver coin"; }
        @Override public boolean matches(String name) { return matchRef(SILVER_COIN, name); }
        @Override public String details() { return "This silver coin is light and still somewhat shiny."; }
        @Override public int weight() { return 5; }
    }

    public static class GoldCoin extends Coin {
        public GoldCoin() { }
        public GoldCoin(Coordinate c) { super(c); }
        @Override public String name() { return "gold coin"; }
        @Override public boolean matches(String name) { return matchRef(GOLD_COIN, name); }
        @Override public String details() { return "This gold coin is heavy and has a dull lustre."; }
        @Override public int weight() { return 8; }
    }

    public static class GoldenStatue extends AbstractItemTrinket {
        public GoldenStatue() { }
        public GoldenStatue(Coordinate c) { super(c); }
        @Override public String name() { return "Golden Statue"; }
        @Override public boolean matches(String name) { return matchRef(GOLDEN_STATUE, name); }
        @Override public String details() { return "This is going to be worth a lot of money."; }
        @Override public int weight() { return 8; }
    }
}
