package maze;

public interface Interacter {
    public String name();

    public boolean matches(String name);

    public boolean canInteract(AbstractUnit unit);

    public Event interact(Commands trigger); //calls InteractionHandler.run() in override of Event.fire(player);

    public boolean isDone(Stage stage);
}
