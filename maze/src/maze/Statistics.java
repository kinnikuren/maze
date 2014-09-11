package maze;

import static util.Loggers.*;
import static util.Print.*;
import static maze.Priority.*;

import java.util.HashMap;

public class Statistics {
    private static HashMap<String, Integer> globalStats = new HashMap<String, Integer>();

    public static void initialize() {
        globalStats.put("monsterKillCount",0);
        globalStats.put("ratKillCount", 0);
        globalStats.put("roomsExplored",1); //origin room already visited
    }

    public static void globalUpdate(String key) {
        if (globalStats == null) log("wtf this isn't supposed to be null!");
        else {
            int x = globalStats.get(key);
            globalStats.put(key, x+1);
        }
    }

    public static int globalGet(String key) {
        return globalStats.get(key);
    }

    public static void globalPrintStats() {
        print("Player Statistics:");
        for (String key : globalStats.keySet()) {
            print(">" + key + ": " + globalStats.get(key));
        }
    }
}
