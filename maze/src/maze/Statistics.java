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
