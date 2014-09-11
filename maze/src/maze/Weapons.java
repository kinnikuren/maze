package maze;

import static maze.References.*;

import java.util.HashMap;

public final class Weapons {
    public static class Dagger extends AbstractItemWeapon {
        private String name;
        private util.SuffixGenerator sg = new util.SuffixGenerator();
        private String suffix = sg.getSuffix();
        private String type = "Main Hand";
        protected HashMap<References, Integer> stats = new HashMap<References, Integer>();

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
        public String type() {
            return type;
        }

        @Override
        public HashMap<References, Integer> getStats() {
            return stats;
        }
    }

    public static class LongSword extends AbstractItemWeapon {
        private String name = "Longsword";
        private String type = "Main Hand";
        protected HashMap<References, Integer> stats = new HashMap<References, Integer>();

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
        public String type() {
            return type;
        }

        @Override
        public HashMap<References, Integer> getStats() {
            return stats;
        }
    }
}
