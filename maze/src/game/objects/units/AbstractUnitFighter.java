package game.objects.units;

import game.core.events.Event;
import game.core.inputs.Commands;
import game.core.interfaces.Fighter;
import game.core.interfaces.Actor;
import game.core.interfaces.Stage;
import game.core.positional.Coordinate;
import game.objects.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class AbstractUnitFighter extends AbstractUnit
implements Fighter {
    int defaultAttackVal;
    int attackVal;
    int defaultDefenseVal;
    int defenseVal;
    int damageLow;
    int damageHigh;

    Inventory inventory = new Inventory();

    private Random rand = new Random();

    public AbstractUnitFighter() {
        super();
        this.resetAttack();
        this.resetDefense();
    }

    public AbstractUnitFighter(Coordinate c) {
        super(c);
        this.resetAttack();
        this.resetDefense();
    }

    public Inventory inventory() { return inventory; }

    public Set<String> getStatusList() { return statusList; }

    public boolean addStatus(String status) {
      return this.statusList.add(status);
    }

    public String getStatusEffect() {
      return this.statusEffect;
    }

    public int getDamage() {
      return (rand.nextInt(damageHigh-damageLow) + damageLow);
    }

    @Override //overrides Unit method
    public void defineTypeDefaultValues() {
        this.defaultHitPoints = 0;
        this.defaultAttackVal = 0;
        this.defaultDefenseVal = 0;
        this.statusEffect = null;

        this.damageLow = 1;
        this.damageHigh = 2;
    }
    //begin Actor methods, abstract
    @Override public abstract boolean matches(String name);
    @Override public abstract boolean canInteract(AbstractUnit unit);
    @Override public abstract Event interact(Commands trigger);
    @Override public abstract boolean isDone(Stage stage);

    @Override
    public abstract Event interact(Commands trigger, String prep, Actor interactee);

    @Override
    public abstract Event interact(Commands trigger, String prep, Actor interactee, Stage secondStage);

    //begin fighter methods
    @Override
    public int attack(int enemyDefense, boolean crit) {
        int damage = this.attackVal;
        if (crit) {
            damage *= 2;
        }
        int result = damage - enemyDefense;
      return result < 0 ? 0 : result;
    }
    @Override
    public int getAttack() { return this.attackVal; }
    @Override
    public void resetAttack()  { this.attackVal = this.defaultAttackVal; }
    @Override
    public void addAttack(int attackAdd) { this.attackVal += attackAdd; }
    @Override
    public int getDefense() { return this.defenseVal; }
    @Override
    public void resetDefense() { this.defenseVal = this.defaultDefenseVal; }
    @Override
    public void addDefense(int defenseAdd) { this.defenseVal += defenseAdd; }
    @Override
    public abstract String battlecry();
    @Override
    public String inspect() {
      return ("The " + this.name() + " does nothing.");
    }
}
