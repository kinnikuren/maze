package maze;

import static maze.Events.equip;
import static maze.References.*;
import static util.Print.*;

import java.util.HashMap;

public final class Armor  {

    public static abstract class Helmet extends AbstractItemArmor {
        private Helmet() { super(); }
        private Helmet(Coordinate c) { super(c); }
    }

    public static class BrownFedora extends Helmet {
        protected HashMap<References, Integer> stats = new HashMap<References, Integer>();

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
        public String type() {
            return "Head";
        }
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

    }

    //Testing
    public static void main(String[] args) {
        BrownFedora fed = new BrownFedora();

        print(fed.details());
        print(fed.type());

    }
}
