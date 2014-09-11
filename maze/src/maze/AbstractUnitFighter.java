package maze;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractUnitFighter extends AbstractUnit
implements Fighter {
    int defaultAttackVal;
    int attackVal;
    int defaultDefenseVal;
    int defenseVal;
    List<String> status = new ArrayList<String>();
    String statusEffect;

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

    public void setStatus(String status) {
        this.status.add(status);
    }

    public String getStatusEffect() {
        return this.statusEffect;
    }

    @Override //overrides Unit method
    public void defineTypeDefaultValues() {
        this.defaultHitPoints = 0;
        this.defaultAttackVal = 0;
        this.defaultDefenseVal = 0;
        this.statusEffect = null;
    }
    //begin Interacter methods, abstract
    @Override public abstract boolean matches(String name);
    @Override public abstract boolean canInteract(AbstractUnit unit);
    @Override public abstract Event interact(Commands trigger);
    @Override public abstract boolean isDone(Stage stage);

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
