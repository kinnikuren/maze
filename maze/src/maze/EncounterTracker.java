package maze;

import static util.Print.print;

import java.lang.reflect.Method;
import java.util.HashMap;

public class EncounterTracker {
    private HashMap<String, Integer> tracker;

    public EncounterTracker() {
        this.tracker = new HashMap<String, Integer>();
        this.fillMap();
    }

    public void track(String encounterName) {
        if (tracker.get(encounterName) == null) {
            tracker.put(encounterName, 1);
        } else {
            int x = tracker.get(encounterName);
            tracker.put(encounterName, x+1);
        }
    }

    private void fillMap() {
        Method[] m = Parade.class.getDeclaredMethods();
        for (int i=0;i<m.length;i++) {
            this.tracker.put(m[i].getName(), 0);
        }
    }

    public void printMap() {
        for (String key : this.tracker.keySet()) {
            print(">" + key + ": " + this.tracker.get(key));
        }
    }

    public HashMap<String, Integer> getTracker() { return this.tracker; }
}
