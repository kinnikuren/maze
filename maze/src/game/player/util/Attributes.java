package game.player.util;

import java.util.Arrays;
import java.util.List;

public enum Attributes {
    //9000-9099 Attributes
    DEX(9001, new String[] {"dexterity", "dex"}),
    STR(9002, new String[] {"strength", "str"}),
    INT(9003, new String[] {"intelligence", "int"}),
    WDLOW(9010, new String[] {"weapon damage (low)"}),
    WDHIGH(9011, new String[] {"weapon damage (high)"});

    public final int classId;
    private final List<String> shortcuts;

    Attributes(int classId, String[] shortcuts) {
        this.shortcuts = Arrays.asList(shortcuts);
        this.classId = classId;
    }

}
