package game.objects.items;

import game.core.events.Event;
import game.core.inputs.Commands;
import game.core.interfaces.Actor;
import game.core.interfaces.Stage;
import game.core.positional.Coordinate;
import game.objects.units.AbstractUnit;
import game.objects.units.Player;

public abstract class AbstractItemFixture extends AbstractItem
implements Actor {
    boolean isVisited = false;
    boolean doAnnounce = true;

    public AbstractItemFixture() {
        super();
        canCarry = false;
    }

    public void visitedBy(Player player) {
        isVisited = true;
    }
    //begin Actor methods, abstract
    @Override public abstract boolean matches(String name);
    @Override public abstract boolean canInteract(AbstractUnit unit);
    @Override public abstract Event interact(Commands trigger);
    @Override public abstract boolean isDone(Stage stage);

    @Override
    public Event interact(Commands trigger, String prep, Actor interactee) { return null; }

    @Override
    public Event interact(Commands trigger, String prep, Actor interactee, Stage secondStage) {
        return null;
    }
}
