package maze;

public interface Interacter {
    public String name();

    public boolean matches(String name);

    public boolean canInteract(AbstractUnit unit);

    //calls InteractionHandler.run() in override of Event.fire(player);
    public Event interact(Commands trigger);

    public Event interact(Commands trigger, String prep, Interacter interactee);

    public boolean isDone(Stage stage);

    public int getMaxSpawn();

    public void setMaxSpawn(int maxSpawn);

    public String getRarity();

    public void setRarity(String rarity);
}
