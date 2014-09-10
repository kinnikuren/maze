package maze;

import java.util.Arrays;
import java.util.List;

public enum References {
    SKELETON(100, new String[] {"skeleton", "skelly"}),
    RAT(101,new String[] {"rat"}),
    RABIDRAT(102,new String[] {"rabid rat"}),
    GOBLIN(110, new String[] {"goblin"}),
    IDOL(200, new String[] {"idol"}),
    CONSOLE(201, new String[] {"mysterious console", "console"}),
    DOODAD(1000, new String[] {"doodad"}),
    BRONZE_COIN(1101, new String[] {"bronze coin", "coin"}),
    SILVER_COIN(1102, new String[] {"silver coin", "coin"}),
    GOLD_COIN(1103, new String[] {"gold coin", "coin"}),
    GOLDEN_STATUE(1104, new String[] {"golden statue"}),
    COMPASS(1105, new String[] {"compass"}),
    HEALING_POTION(1201, new String[] {"healing potion", "potion"}),
    APPLE(1202, new String[] {"apple"}),
    DAGGER(2000, new String[] {"dagger"}),
    DEX(9001, new String[] {"dexterity", "dex"}),
    STR(9002, new String[] {"strength", "str"}),
    INT(9003, new String[] {"intelligence", "int"}),
    ERROR(9999, new String[] {"ERROR"});

    public final int classId;
    private final List<String> shortcuts;

    References(int classId, String[] shortcuts) {
        this.shortcuts = Arrays.asList(shortcuts);
        this.classId = classId;
    }

    public static References get(String str) {
        for (References ref : References.values()) {
            if (ref.shortcuts.contains(str))
              return ref;
        }
      return ERROR; //if nothing matches
    }

    public static List<String> getShortcuts(References ref) {
      return ref.shortcuts;
    }

    public static boolean matchRef(References ref, String name) {
        for (String shortcut : ref.shortcuts) {
          if (shortcut.equalsIgnoreCase(name))
            return true;
        }
      return false;
    }

    public static References get(int classID) {
        for (References ref : References.values()) {
            if (ref.classId == classID)
              return ref;
        }
      return ERROR; //if nothing matches
    }
}
