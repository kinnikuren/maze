package maze;

public final class SpawningPool {

    public static abstract class Spawner { public abstract Interacter spawn(); }

    public static abstract class GoblinSpawner extends Spawner {  }

    public static abstract class SkeletonSpawner extends Spawner {  }

    public static abstract class RatSpawner extends Spawner {  }
    public static abstract class RatKingSpawner extends Spawner {  }

    public static abstract class OilyRagSpawner extends Spawner { }
    public static abstract class WoodenStickSpawner extends Spawner { }
    public static abstract class MatchesSpawner extends Spawner { }

    public static abstract class DaggerSpawner extends Spawner { }
    public static abstract class LongswordSpawner extends Spawner { }

    public static abstract class FedoraSpawner extends Spawner { }

    public static abstract class IdolSpawner extends Spawner { }
    public static abstract class ConsoleSpawner extends Spawner { }

    public static abstract class BronzeCoinSpawner extends Spawner {  }
    public static abstract class SilverCoinSpawner extends Spawner {  }
    public static abstract class GoldCoinSpawner extends Spawner {  }

}
