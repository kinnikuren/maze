package maze;

import static maze.References.*;

public final class Weapons {
    public static class Dagger extends AbstractItemWeapon {
        private String name;
        private maze.SuffixGenerator sg = new maze.SuffixGenerator();
        private String suffix = sg.getSuffix();

        public Dagger() {
            super();
            this.name = "Dagger " + suffix;
        }

        public Dagger(Coordinate c) { super(c); }

        //public int[] weaponDamage = {0, 0};

        @Override
        public void defineTypeDefaultValues() {
            this.weaponDamage[0] = 2;
            this.weaponDamage[1] = 4;
            //this.weaponDamage = 2;
        }

        public int getWeaponDamage() {
            return this.weaponDamage[0];
        }

        @Override public String name() { return name; }
        @Override public boolean matches(String name) { return matchRef(DAGGER, name); }
        @Override public String details() { return "This dagger's fancy name is irrelevant.  It is rusty and in poor condition.  On the plus side, you could probably give someone tetanus with this weapon."; }
        @Override public int weight() { return 10; }
    }
}
