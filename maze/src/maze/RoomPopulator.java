package maze;

import java.util.Set;

import maze.SpawningPool.Spawner;

public class RoomPopulator {
    private Set<Spawner> spawnSet;

    public Set<Spawner> getSpawnSet() {
        return spawnSet;
    }

    public void setSpawnSet(Set<Spawner> spawnSet) {
        this.spawnSet = spawnSet;
    }

}
