package maze;

import java.util.Random;

public final class Spells {
    private static Random rand = new Random();

    protected static abstract class Spell {
        protected int damageLow;
        protected int damageHigh;

        private Spell() {
            this.damageLow = 0;
            this.damageHigh = 1;
        }

        public int getDamage() {
            return (rand.nextInt(damageHigh-damageLow) + damageLow);
        }

    }

    public static class FireStorm extends Spell {
        public FireStorm() {
            this.damageLow = 10;
            this.damageHigh = 20;
        }

        @Override
        public String toString() { return "FireStorm"; }
    }

}
