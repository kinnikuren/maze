package game.core.interfaces;

import game.core.events.Event;
import game.core.inputs.Commands;
import game.objects.units.AbstractUnit;
import game.objects.units.Player;

public interface Actor {
    public String name();

    public boolean matches(String name);

    public boolean canInteract(AbstractUnit unit);

    //calls InteractionHandler.run() in override of Event.fire(player);
    public Event interact(Commands trigger);

    public Event interact(Commands trigger, String prep, Actor interactee);

    public Event interact(Commands trigger, String prep, Actor interactee, Stage secondStage);

    public boolean isDone(Stage stage);

    public int getMaxSpawn();

    public void setMaxSpawn(int maxSpawn);

    public String getRarity();

    public void setRarity(String rarity);
}
