package game.core.interfaces;

public interface Flammable {
    public void lightOnFire(Flame flame);
    public boolean isOnFire();
    public void extinguish();

    public static interface Flame {
    }
}


