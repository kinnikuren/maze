package game.core.maze;

import game.content.general.SpawningPool;
import game.content.general.SpawningPool.Spawner;

import java.util.Set;

public class RoomPopulator {
    private Set<Spawner> spawnSet;

    public Set<Spawner> getSpawnSet() {
        return spawnSet;
    }

    public void setSpawnSet(Set<Spawner> spawnSet) {
        this.spawnSet = spawnSet;
    }

}
