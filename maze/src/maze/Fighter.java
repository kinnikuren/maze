package maze;

public interface Fighter extends Interacter {

    public int attack(int enemyDefense, boolean crit);

    public int getAttack();

    public void resetAttack();

    public void addAttack(int attackAdd);

    public int getDefense();

    public void resetDefense();

    public void addDefense(int defenseAdd);

    public int hp();

    public void resetHP();

    public void setHP(int hp);

    public void addHP(int hp);

    public void loseHP(int hp);

    public String battlecry();

    public String inspect();

    public void death();

    public String getStatusEffect();
}
