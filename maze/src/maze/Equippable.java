package maze;

public interface Equippable extends Portable {

    public int getWeaponDamage();

    public void equipped();

    public void unequipped();

}
