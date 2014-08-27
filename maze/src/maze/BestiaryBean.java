package maze;

public class BestiaryBean {
    String name;
    int attackValue;
    int defenseValue;
    int hitPoints;
    String statusEffect;
    String status;
    String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttackValue() {
        return attackValue;
    }

    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
    }

    public int getDefenseValue() {
        return defenseValue;
    }

    public void setDefenseValue(int defenseValue) {
        this.defenseValue = defenseValue;
    }

    public int gethitPoints() {
        return hitPoints;
    }

    public void sethitPoints(int HP) {
        this.hitPoints = HP;
    }

    public String getStatusEffect() {
        return statusEffect;
    }

    public void setStatusEffect(String effect) {
        this.statusEffect = effect;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
