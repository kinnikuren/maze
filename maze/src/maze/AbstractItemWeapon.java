package maze;

public abstract class AbstractItemWeapon extends AbstractItemEquippable {

    int[] weaponDamage = new int[2];

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

}
