package tests;

import game.content.general.SpawningPool.Spawner;

import java.util.Set;

public class RoomPopulator {
    private Set<Spawner> spawnSet;
    private Set<Spawner> gateSet;

    public Set<Spawner> getSpawnSet() {
        return spawnSet;
    }

    public void setSpawnSet(Set<Spawner> spawnSet) {
        this.spawnSet = spawnSet;
    }

}
