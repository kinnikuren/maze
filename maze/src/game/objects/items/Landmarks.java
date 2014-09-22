package game.objects.items;

import static game.objects.general.References.*;
import static game.core.inputs.Commands.*;
import game.core.events.Event;
import game.core.events.Events;
import game.core.events.Priority;
import game.core.inputs.Commands;
import game.core.interfaces.Stage;
import game.objects.general.References;
import game.objects.units.AbstractUnit;
import game.objects.units.Player;

public final class Landmarks {

    public static abstract class AbstractLandmark extends AbstractItemFixture {
        protected References ref = null;
        protected String desc = null;
        protected String name = "Landmark";

        protected AbstractLandmark() {
            super();
            this.maxSpawn = 1;
            this.rarity = "pretty common";
        }

        @Override
        public boolean canInteract(AbstractUnit unit) {
          return unit instanceof Player ? true : false;
        }

        @Override
        public String name() { return name; }

        @Override
        public boolean matches(String name) {
          return matchRef(ref, name);
        }

        @Override
        public boolean isDone(Stage stage) {
            return false;
        }

        @Override
        public Event interact(Commands trigger) {
            Event event = null;
            if (trigger == DESCRIBE) {
                event = Events.announce(this, Priority.LOW, desc);
            }
            return event;
        }

    }

    public static class GnarledTree extends AbstractLandmark{
        public GnarledTree() {
            super();
            this.ref = GNARLED_TREE;
            this.desc = "This old tree is most gnarly.";
            this.name = "Gnarled Tree";
        }
    }

    public static class LeafyTree extends AbstractLandmark{
        public LeafyTree() {
            super();
            this.ref = LEAFY_TREE;
            this.desc = "There are a lot of leaves on this tree.";
            this.name = "Leafy Tree";
        }
    }

    public static class DeadTree extends AbstractLandmark{
        public DeadTree() {
            super();
            this.ref = DEAD_TREE;
            this.desc = "This tree has gone to tree heaven.";
            this.name = "Dead Tree";
        }
    }

    public static class ClownStatue extends AbstractLandmark{
        public ClownStatue() {
            super();
            this.ref = CLOWN_STATUE;
            this.desc = "A really, creepy statue of a clown. Why would anyone build this?";
            this.name = "Clown Statue";
        }
    }

    public static class Gallows extends AbstractLandmark {
        public Gallows() {
            super();
            this.ref = GALLOWS;
            this.desc = "Some poor sap is strung up here. Looks like his neck didn't break so "
                    + "he suffocated to death.";
            this.name = "Gallows";
        }
    }

    public static class ThreeDPainting extends AbstractLandmark {
        public ThreeDPainting() {
            super();
            this.ref = THREED_PAINTING;
            this.desc = "It's a 3-D painting. The patterns are mesmerizing.";
            this.name = "3-D Painting";
        }
    }

    public static class Lamppost extends AbstractLandmark {
        public Lamppost() {
            super();
            this.ref = LAMPPOST;
            this.desc = "A lone lamppost with low luminescence lights the location.";
            this.name = "Lamppost";
        }
    }

    public static class StoneGolem extends AbstractLandmark {
        public StoneGolem() {
            super();
            this.ref = STONE_GOLEM_INACTIVE;
            this.desc = "A huge stone golem stands impassively in the room.";
            this.name = "Stone Golem";
        }
    }

    public static class Gargoyle extends AbstractLandmark {
        public Gargoyle() {
            super();
            this.ref = GARGOYLE_INACTIVE;
            this.desc = "A grotesque gargoyle. Its stone eyes somehow seem to follow your "
                    + "movements.";
            this.name = "Stone Gargoyle";
        }
    }

    public static class Mirror extends AbstractLandmark {
        public Mirror() {
            super();
            this.ref = MIRROR;
            this.desc = "An ancient, dusty mirror hangs on the wall. You can barely see your own "
                    + "reflection. You notice that your mirror counterpart sometimes moves "
                    + "independently of you...";
            this.name = "Mirror";
        }
    }

    public static class TwoFerns extends AbstractLandmark {
        public TwoFerns() {
            super();
            this.ref = TWO_FERNS;
            this.desc = "It's a pair of ferns.";
            this.name = "Two Ferns";
        }
    }

    public static class EmptyThrone extends AbstractLandmark {
        public EmptyThrone() {
            super();
            this.ref = THRONE;
            this.desc = "A glorious, golden throne, devoid of any kingly occupants.";
            this.name = "Empty Throne";
        }
    }

    public static class Altar extends AbstractLandmark {
        public Altar() {
            super();
            this.ref = ALTAR;
            this.desc = "A sacrificial altar stands in the middle of the room. Viscera and dried "
                    + "blood are caked on the surface.";
            this.name = "Sacrificial Altar";
        }
    }

    public static class GildedDoor extends AbstractLandmark {
        public GildedDoor() {
            super();
            this.ref = GILDED_DOOR;
            this.desc = "This beautiful, gilded door gives you hope that you've finally found "
                    + "the exit to the maze! Your hopes are soon dashed upon opening the door "
                    + "and seeing a blank wall.";
            this.name = "Gilded Door";
        }
    }

    public static class Hieroglyphics extends AbstractLandmark {
        public Hieroglyphics() {
            super();
            this.ref = HIEROGLYPHICS;
            this.desc = "Strange hieroglyphics adorn the walls of this room. Hoping to find clues "
                    + "pointing to the exit, you only see some useless pictures of pyramids and "
                    + "spaceships.";
            this.name = "Hieroglyphics";
        }
    }

    public static class Skylight extends AbstractLandmark {
        public Skylight() {
            super();
            this.ref = SKYLIGHT;
            this.desc = "It's a skylight! You can hardly recall the last time you've seen the sun. "
                    + "Too bad it's out of reach.";
            this.name = "Skylight";
        }
    }

    public static class Hopscotch extends AbstractLandmark {
        public Hopscotch() {
            super();
            this.ref = HOPSCOTCH;
            this.desc = "A hopscotch court is drawn on the floor in chalk. You can hear children "
                    + "giggling but there's no one here but you.";
            this.name = "Hopscotch Court";
        }
    }

    public static class Madman extends AbstractLandmark {
        public Madman() {
            super();
            this.ref = MADMAN;
            this.desc = "Another living soul! Your attempts to talk are useless however. The man "
                    + "seems preoccupied with mumbling to himself and banging his head against "
                    + "the wall.";
            this.name = "Raving Madman";
        }
    }

    public static class PillarOfLight extends AbstractLandmark {
        public PillarOfLight() {
            super();
            this.ref = PILLAR_OF_LIGHT;
            this.desc = "A glorious pillar of light seemingly sent from the Heavens! Standing in it "
                    + "does nothing...";
            this.name = "Pillar of Light";
        }
    }

    public static class Toilet extends AbstractLandmark {
        public Toilet() {
            super();
            this.ref = TOILET;
            this.desc = "A...toilet? It looks ancient and used. You dare not look inside. Thank "
                    + "goodness you remembered to use the restroom before being mysteriously "
                    + "sucked into this maze.";
            this.name = "Toilet";
        }
    }
}
