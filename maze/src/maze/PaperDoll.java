package maze;

import static util.Loggers.log;
import static util.Print.print;
import static util.Utilities.checkNullArg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import util.NullArgumentException;

import com.google.common.collect.ArrayListMultimap;

public class PaperDoll implements Stage {
    private HashMap<String,Equippable> paperDoll = new HashMap<String,Equippable>();
    //ArrayList<Interacter> contents = new ArrayList<Interacter>();
    ArrayList<Interacter> interactions = new ArrayList<Interacter>();
    EventManager manager;

    public PaperDoll() {
        this.manager = new EventManager(interactions);
        this.paperDoll.put("Main Hand", null);
        this.paperDoll.put("Head", null);
        this.paperDoll.put("Body", null);
    }

    public Equippable getWeapon() {
        return paperDoll.get("Main Hand");
    }

  //@throws[NullArgumentException]
    public Equippable add(Equippable item) throws NullArgumentException {
        checkNullArg(item);

        log("Attempting to add " + item + " to paper doll...");
        Equippable oldItem = null;

        //oldItem = paperDoll.replace(item.type(), item);
        if (!paperDoll.keySet().contains(item.type())) {
            oldItem = paperDoll.put(item.type(), item);
        }

        manager.interactions().add(item);
        log("Item added to paperdoll interactions: " + interactions);

        if (oldItem != null) {
            manager.interactions().remove(oldItem);
            log("Previous item removed from paperdoll interactions: " + interactions);
        }

        return oldItem;
    }

    public Equippable remove(Equippable item) {

        if (paperDoll.containsValue(item)) {
            paperDoll.put(item.type(), null);
            manager.interactions().remove(item);
            log(item + " removed from paperdoll interactions: " + interactions);
            return item;
        } else {
            return null;
        }
    }

    public boolean contains(Equippable item) {
        log("Checking if paperdoll contains " + item + "...");
        return paperDoll.containsValue(item);
    }

    @Override
    public boolean contains(String itemName) {
        return manager.contains(itemName);
    }

    public String toString() {
        String inventoryContents = "Equipment Loadout: ";
        for (String slot : paperDoll.keySet()) {
            String addDescription= "\n " + slot + ": ";
            if (paperDoll.get(slot) != null)
                addDescription += paperDoll.get(slot).name();
            else
                addDescription += "(none)";
            inventoryContents += addDescription;
        }
      return inventoryContents;
    }

    @Override
    public void removeActor(Interacter actor) {
        try {
            Equippable item = (Equippable)actor;
            remove(item);
        } catch (ClassCastException cce) {
            log("This is not a valid actor to remove from inventory!");
        }
    }


    @Override
    public void cleanupActors() {
        for (Interacter actor : interactions) {
            if (actor.isDone(this)) removeActor(actor);
        }
    }

    @Override
    public boolean isBarren() {
        return manager.interactions().size() == 0 ? true : false;
    }

    @Override
    public String getName() { return "your loadout"; }

    @Override
    public PriorityQueue<Event> getCurrentEvents() {
        return manager.getCurrentEvents();
    }

    @Override
    public PriorityQueue<Event> getCurrentEvents(Commands trigger) {
        return manager.getCurrentEvents(trigger);
    }

    @Override
    public PriorityQueue<Event> getCurrentEvents(Commands trigger,
            String objectName) {
        return manager.getCurrentEvents(trigger, objectName);
    }

    //Testing
    public static void main(String[] args) {
        PaperDoll pd = new PaperDoll();
        Weapons.Dagger dg = new Weapons.Dagger();
        Weapons.Dagger dg1 = new Weapons.Dagger();
        Armor.BrownFedora fed = new Armor.BrownFedora();
        Armor.SuperSuit ss = new Armor.SuperSuit();
        print(fed.type());
        Equippable oldItem = null;

        print(pd.toString());

        print("Adding items...");
        oldItem = pd.add(dg);
        pd.add(fed);
        pd.add(ss);

        print("Dropped " + oldItem);
        print(pd.toString());

        print("Replacing dagger...");
        oldItem = pd.add(dg1);

        print("Dropped " + oldItem);
        print(pd.toString());
        print("Interactions " + pd.manager.interactions());

        print("Removing items...");
        pd.removeActor(dg1);
        pd.removeActor(fed);
        pd.removeActor(ss);
        print(pd.toString());
    }
}
