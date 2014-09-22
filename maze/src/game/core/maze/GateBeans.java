package game.core.maze;

public final class GateBeans {

    public static class GateBean {
        String gateName;
        Double maxDistance;
        int maxSpawn;
        boolean spawnKey;

        public GateBean() { }

        public String getGateName() {
            return gateName;
        }
        public void setGateName(String gateName) {
            this.gateName = gateName;
        }
        public Double getMaxDistance() {
            return maxDistance;
        }
        public void setMaxDistance(Double maxDistance) {
            this.maxDistance = maxDistance;
        }
        public int getMaxSpawn() {
            return maxSpawn;
        }
        public void setMaxSpawn(int maxSpawn) {
            this.maxSpawn = maxSpawn;
        }
        public boolean isSpawnKey() {
            return spawnKey;
        }
        public void setSpawnKey(boolean spawnKey) {
            this.spawnKey = spawnKey;
        }
    }

    public static class GenericGateBean extends GateBean { }

    public static class RedDoorBean extends GateBean { }

    public static class BlueDoorBean extends GateBean { }

    public static class PurpleDoorBean extends GateBean { }

}
