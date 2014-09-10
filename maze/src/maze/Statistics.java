package maze;

import static util.Loggers.*;
import static util.Print.*;
import static maze.Priority.*;

import java.util.HashMap;

public class Statistics {
    int monsterKillCount;
    int ratKillCount;
    int roomsExplored;
    HashMap<String, Integer> stats = new HashMap<String, Integer>();
    private static HashMap<String, Integer> globalStats = new HashMap<String, Integer>();

    public Statistics() {
        this.monsterKillCount = 0;
        this.roomsExplored = 0;
        this.stats.put("monsterKillCount",0);
        this.stats.put("ratKillCount", 0);
        this.stats.put("roomsExplored",1); //origin room already visited
    }

    public void monsterKill() {
        this.monsterKillCount++;
        int x = this.stats.get("monsterKillCount");
        this.stats.put("monsterKillCount", x+1);
        log("Monster Kill Count: " + this.monsterKillCount,LOW);
        log("Monster Kill Count (hash map): " + this.monsterKillCount,LOW);
    }

    public void update(String key) {
        int x = this.stats.get(key);
        this.stats.put(key, x+1);
    }

    public static void initialize() {
        globalStats.put("monsterKillCount",0);
        globalStats.put("ratKillCount", 0);
        globalStats.put("roomsExplored",1); //origin room already visited
    }

    public static void globalUpdate(String key) {
        int x = globalStats.get(key);
        globalStats.put(key, x+1);
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

    public int getKillCount() {
        return this.monsterKillCount;
    }

    public void printStats() {
        print("Player Statistics:");
        for (String key : this.stats.keySet()) {
            print(">" + key + ": " + this.stats.get(key));
        }
    }
}
