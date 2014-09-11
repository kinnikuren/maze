package maze;

public interface Equippable extends Portable {

    public void equipped();

    public void unequipped();

    public String type();

}
