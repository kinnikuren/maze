package game.objects.items;

public abstract class AbstractItem {
    String name; //populated via getClass by default
    boolean canCarry = true;
    int maxSpawn = 1;
    String rarity="common";

    public AbstractItem() {
        this.name = this.getClass().getSimpleName();
    }

    public String name() { return name; }

    public void setName(String newName) { this.name = newName; }

    @Override
    public String toString() { return this.name(); }

    public int getMaxSpawn() {
        return maxSpawn;
    }

    public void setMaxSpawn(int maxSpawn) {
        this.maxSpawn = maxSpawn;
    }

    public String getRarity() { return rarity; }

    public void setRarity(String rarity) { this.rarity = rarity; }

}
