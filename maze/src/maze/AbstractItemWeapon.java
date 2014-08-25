package maze;

import static maze.Events.*;
import static maze.Priority.HIGH;

public abstract class AbstractItemWeapon extends AbstractItemPortable
implements Equippable {
    private boolean pickedUp = false;
    private boolean equipped = false;

    int[] weaponDamage = new int[2];
    //int weaponDamage;

    public AbstractItemWeapon() {
        super();
        //this.weaponDamage = new int[]{0,0};
        //this.weaponDamage = 2;
        this.defineTypeDefaultValues();
    };

    public AbstractItemWeapon(Coordinate c) {
        super(c);
        //this.weaponDamage = new int[]{0,0};
        //this.weaponDamage = 2;
        this.defineTypeDefaultValues();
    }

    public abstract void defineTypeDefaultValues();
    //{
        //this.weaponDamage[0] = 0;
        //this.weaponDamage[1] = 0;
    //};

    @Override
    public void equipped() { equipped = true; }
    @Override
    public void unequipped() { equipped = false; }

    @Override public abstract boolean matches(String name);
    @Override
    public boolean canInteract(AbstractUnit unit) {
        return false;
    }
    @Override
    public Event interact(Commands trigger) {
        Event event = super.interact(trigger);
        if (event == null) {
            return trigger == Commands.EQUIP ? equip(this) : null;
        }
        else return event;
    }

    @Override
    public boolean isDone(Stage stage) {
        return pickedUp;
    }
    /*
    public boolean isDone(Stage stage) {
        if (stage instanceof AbstractRoom) return pickedUp;
        if (stage instanceof Inventory) return !pickedUp && equipped;
        return false;
    }
    */
    @Override
    public void pickedUp() { pickedUp = true; }
    @Override
    public void dropped() { pickedUp = false; }
    @Override public abstract String details();
    @Override public abstract int weight();

}
