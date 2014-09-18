package tests;

import java.util.Arrays;
import java.util.List;

import static game.objects.units.Bestiary.*;

public enum ReferencesTest {
    //100 - 149 Normal Monsters
    SKELETON(100, false, new String[] {"skeleton", "skelly"}, new Skeleton()),
    /*
    RAT(101, false, new String[] {"rat"}),
    RABIDRAT(102, false, new String[] {"rabid rat"}),
    GOBLIN(110,false, new String[] {"goblin"}),
    STONE_GOLEM(111, true, new String[] {"stone golem"}),

    //180-199 Boss Monsters
    RATKING(180, true, new String[] {"rat king"}),
    REVANTON(181, true, new String[] {"Revanton"}),

    //200-299 Fixtures
    IDOL(200, true, new String[] {"idol"}),
    CONSOLE(201, true, new String[] {"mysterious console", "console"}),
    DEAD_ADVENTURER(202, true, new String[] {"dead adventurer"}),
    PULLEY(203, true, new String[]{"pulley"}),

    //400-499 NPCs
    VENDOR(400, false, new String[] {"vendor"}),

    //1100-1199 Normal Trinkets
    DOODAD(1000, false, new String[] {"doodad"}),
    BRONZE_COIN(1101, false, new String[] {"bronze coin", "coin"}),
    SILVER_COIN(1102, false, new String[] {"silver coin", "coin"}),
    GOLD_COIN(1103, false, new String[] {"gold coin", "coin"}),
    GOLDEN_STATUE(1104, true, new String[] {"golden statue"}),
    RAT_TAIL(1105, false, new String[] {"rat tail", "tail"}),
    BONEMEAL(1106, false, new String[] {"bonemeal"}),
    RUBBER_CHICKEN(1107, true, new String[] {"rubber chicken"}),
    ENCNONE(1199, true, new String[] {"enc-none"}),

    //1200-1299 Consumable Items
    HEALING_POTION(1201, false, new String[] {"healing potion", "potion"}),
    APPLE(1202, false, new String[] {"apple"}),

    //1300-1399 Reusable Trinkets
    COMPASS(1300, true, new String[] {"compass"}),
    RED_KEY(1301, true, new String[] {"red key"}),
    BLUE_KEY(1302, true, new String[] {"blue key"}),
    PURPLE_KEY(1303, true, new String[] {"purple key"}),
    MATCHES(1304, true, new String[] {"matches"}),
    OILY_RAG(1305, true, new String[] {"oily rag", "rag"}),
    WARPWHISTLE(1399, false, new String[] {"warp whistle"}),

    //2000-2099 Weapons
    DAGGER(2000, false, new String[] {"dagger"}),
    LONGSWORD(2001, true, new String[] {"longsword", "long sword"}),
    WOODEN_STICK(2002, true, new String[] {"stick", "wooden stick"}),
    UNLIT_TORCH(2003, false, new String[] {"unlit torch"}),
    LIT_TORCH(2004, false, new String[] {"lit torch"}),

    //2100-2199 Armor
    FEDORA(2100, true, new String[] {"fedora", "brown fedora"}),
    SUPERSUIT(2199, false, new String[] {"super suit"}),
    */

    ERROR(9999, false, new String[] {"ERROR"}, null);

    public final int classId;
    private final List<String> shortcuts;
    public boolean spawnOnce = false;
    public game.objects.units.AbstractUnit unit;

    ReferencesTest(int classId, boolean spawnOnce, String[] shortcuts, game.objects.units.AbstractUnit unit) {
        this.shortcuts = Arrays.asList(shortcuts);
        this.classId = classId;
        this.spawnOnce = spawnOnce;
        this.unit = unit;
    }

    public game.objects.units.AbstractUnit getUnit() {
        return unit;
    }

    public static ReferencesTest get(String str) {
        for (ReferencesTest ref : ReferencesTest.values()) {
            if (ref.shortcuts.contains(str))
              return ref;
        }
      return ERROR; //if nothing matches
    }

    public static List<String> getShortcuts(ReferencesTest ref) {
      return ref.shortcuts;
    }

    public static boolean matchRef(ReferencesTest ref, String name) {
        for (String shortcut : ref.shortcuts) {
          if (shortcut.equalsIgnoreCase(name))
            return true;
        }
      return false;
    }

    public static ReferencesTest get(int classID) {
        for (ReferencesTest ref : ReferencesTest.values()) {
            if (ref.classId == classID)
              return ref;
        }
      return ERROR; //if nothing matches
    }
}
