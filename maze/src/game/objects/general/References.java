package game.objects.general;

import game.objects.units.AbstractUnit;

import java.util.Arrays;
import java.util.List;

public enum References {
    //100 - 149 Normal Monsters
    SKELETON(100, new String[] {"skeleton", "skelly"}),
    RAT(101, new String[] {"rat"}),
    RABIDRAT(102, new String[] {"rabid rat"}),
    GOBLIN(110, new String[] {"goblin"}),
    STONE_GOLEM(111, new String[] {"stone golem"}),

    //180-199 Boss Monsters
    RATKING(180, new String[] {"rat king"}),
    REVANTON(181, new String[] {"Revanton"}),

    //200-299 Fixtures
    IDOL(200, new String[] {"idol"}),
    CONSOLE(201, new String[] {"mysterious console", "console"}),
    DEAD_ADVENTURER(202, new String[] {"dead adventurer"}),
    PULLEY(203, new String[]{"pulley"}),

    //300-399 Gates
    GATE(300, new String[] {"gate", "locked gate", "unlocked gate"}),
    RED_DOOR(301, new String[] {"red door", "locked red door", "unlocked red door"}),

    //400-499 NPCs
    VENDOR(400, new String[] {"vendor"}),

    //1100-1199 Normal Trinkets
    DOODAD(1000, new String[] {"doodad"}),
    BRONZE_COIN(1101, new String[] {"bronze coin", "coin"}),
    SILVER_COIN(1102, new String[] {"silver coin", "coin"}),
    GOLD_COIN(1103, new String[] {"gold coin", "coin"}),
    GOLDEN_STATUE(1104, new String[] {"golden statue"}),
    RAT_TAIL(1105, new String[] {"rat tail", "tail"}),
    BONEMEAL(1106, new String[] {"bonemeal"}),
    RUBBER_CHICKEN(1107, new String[] {"rubber chicken"}),
    ENCNONE(1199, new String[] {"enc-none"}),

    //1200-1299 Consumable Items
    HEALING_POTION(1201, new String[] {"healing potion", "potion"}),
    APPLE(1202, new String[] {"apple"}),

    //1300-1399 Reusable Trinkets
    COMPASS(1300, new String[] {"compass"}),
    PLAIN_KEY(1306, new String[] {"plain key"}),
    RED_KEY(1301, new String[] {"red key"}),
    BLUE_KEY(1302, new String[] {"blue key"}),
    PURPLE_KEY(1303, new String[] {"purple key"}),
    MATCHES(1304, new String[] {"matches"}),
    OILY_RAG(1305, new String[] {"oily rag", "rag"}),
    WARPWHISTLE(1399, new String[] {"warp whistle"}),

    //1400-1499, One-time Use Trinkets
    DISSOLVING_POTION(1400, new String[] {"dissolving potion"}),

    //2000-2099 Weapons
    DAGGER(2000, new String[] {"dagger"}),
    LONGSWORD(2001, new String[] {"longsword", "long sword"}),
    WOODEN_STICK(2002, new String[] {"stick", "wooden stick"}),
    UNLIT_TORCH(2003, new String[] {"unlit torch"}),
    LIT_TORCH(2004, new String[] {"lit torch"}),

    //2100-2199 Armor
    FEDORA(2100, new String[] {"fedora", "brown fedora"}),
    SUPERSUIT(2199, new String[] {"super suit"}),

    ERROR(9999, new String[] {"ERROR"});

    public final int classId;
    private final List<String> shortcuts;
    public boolean spawnOnce = false;
    public AbstractUnit unit;

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
