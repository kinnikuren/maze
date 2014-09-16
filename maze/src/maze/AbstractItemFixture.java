package maze;

public abstract class AbstractItemFixture extends AbstractItem
implements Interacter {
    boolean isVisited = false;
    boolean doAnnounce = true;

    public AbstractItemFixture() {
        super();
        canCarry = false;
    }

    public AbstractItemFixture(Coordinate c) {
        super(c);
        canCarry = false;
    }

    public void visitedBy(Player player) {
        isVisited = true;
    }
    //begin Interacter methods, abstract
    @Override public abstract boolean matches(String name);
    @Override public abstract boolean canInteract(AbstractUnit unit);
    @Override public abstract Event interact(Commands trigger);
    @Override public abstract boolean isDone(Stage stage);

    @Override
    public Event interact(Commands trigger, String prep, Interacter interactee) { return null; }
}
