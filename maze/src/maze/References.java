package maze;

import java.util.Arrays;
import java.util.List;

public enum References {
    //100 - 149 Normal Monsters
    SKELETON(100, new String[] {"skeleton", "skelly"}),
    RAT(101,new String[] {"rat"}),
    RABIDRAT(102,new String[] {"rabid rat"}),
    GOBLIN(110, new String[] {"goblin"}),

    //180-199 Boss Monsters
    RATKING(180,new String[] {"rat king"}),

    //200-299 Fixtures
    IDOL(200, new String[] {"idol"}),
    CONSOLE(201, new String[] {"mysterious console", "console"}),

    //1100-1199 Normal Trinkets
    DOODAD(1000, new String[] {"doodad"}),
    BRONZE_COIN(1101, new String[] {"bronze coin", "coin"}),
    SILVER_COIN(1102, new String[] {"silver coin", "coin"}),
    GOLD_COIN(1103, new String[] {"gold coin", "coin"}),
    GOLDEN_STATUE(1104, new String[] {"golden statue"}),
    ENCNONE(1105, new String[] {"enc-none"}),

    //1200-1299 Consumable Items
    HEALING_POTION(1201, new String[] {"healing potion", "potion"}),
    APPLE(1202, new String[] {"apple"}),

    //1300-1399 Reusable Trinkets
    COMPASS(1300, new String[] {"compass"}),

    //2000-2099 Weapons
    DAGGER(2000, new String[] {"dagger"}),
    LONGSWORD(2001, new String[] {"longsword", "long sword"}),

    //2100-2199 Armor
    FEDORA(2100, new String[] {"fedora", "brown fedora"}),
    SUPERSUIT(2199, new String[] {"super suit"}),

    //9000-9099 Skills
    DEX(9001, new String[] {"dexterity", "dex"}),
    STR(9002, new String[] {"strength", "str"}),
    INT(9003, new String[] {"intelligence", "int"}),
    WDLOW(9010, new String[] {"weapon damage (low)"}),
    WDHIGH(9011, new String[] {"weapon damage (high)"}),

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
