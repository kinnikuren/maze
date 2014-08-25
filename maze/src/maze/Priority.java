package maze;

public enum Priority implements Comparable<Priority> {
    DORMANT(0, "DORMANT"),
    LOW(1, "LOW"),
    DEFAULT(2, "MEDIUM"),
    HIGH(3, "HIGH"),
    MAX(4, "MAX");

    private final int level;
    private final String name;

    Priority(int level, String name) {
        this.level = level;
        this.name = name;
    }

    public String toString() { return name; }

    public static int getLevel(Priority p) { return p.level; }

    public static Priority get(int level) {
        for (Priority p : Priority.values()) {
          if (p.level == level) return p;
        }
      return null;
    }

    public static Priority get(String name) {
        for (Priority p : Priority.values()) {
          if (p.name.equalsIgnoreCase(name)) return p;
        }
      return null;
    }

    public static Priority downShift(Priority p) {
        if (p == Priority.DORMANT) return p;
        else {
            int lowerLevel = Priority.getLevel(p) - 1;
          return Priority.get(lowerLevel);
        }
    }

    public static Priority upShift(Priority p) {
        if (p == Priority.MAX) return p;
        else {
            int higherLevel = Priority.getLevel(p) + 1;
          return Priority.get(higherLevel);
        }
    }
}
