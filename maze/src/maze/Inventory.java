package maze;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import static util.Loggers.*;
import util.GrammarGuy;
import util.NullArgumentException;

import com.google.common.collect.ArrayListMultimap;

import static util.Print.print;
import static util.Print.wordWrapPrint;
import static util.Utilities.*;

public class Inventory implements Stage {
    private ArrayListMultimap<String, Portable> inventory = ArrayListMultimap.create();
    ArrayList<Interacter> contents = new ArrayList<Interacter>();
    ArrayList<Interacter> interactions = new ArrayList<Interacter>();
    EventManager manager;

    public Inventory() {
        this.manager = new EventManager(interactions);
    }

    //@throws[NullArgumentException]
    public void add(Portable item) throws NullArgumentException {
        checkNullArg(item);
        inventory.put(item.name(), item);
        interactions.add(item);
        log("Item added to inventory interactions: " + interactions);
    }

    public Portable remove(Portable item) {
        String itemName = item.name();
        Portable removed = null;
        for (Portable i : inventory.get(itemName)) {
          if (i == item) {
            removed = i;
          }
        }
        if (removed != null) {
            inventory.remove(itemName, removed);
            interactions.remove(removed);
            log(removed + " removed from inventory interactions: " + interactions);
        }
        return removed;
    }

    public boolean remove(String itemName) {
        //removes one item, if it exists, from the items associated with the given name.
        //which item is removed is NOT specified!
        boolean removed = false;
        Random rand = new Random();
        //get list of Portables associated with the string
        List<Portable> items = inventory.get(itemName);
        if (items.size() > 0) {
            //generates random number between 0 and size-1
            int index = rand.nextInt(items.size());
            //creates an array from the list of Portables
            Portable[] itemsArr = items.toArray(new Portable[items.size()]);
            Portable candidate = itemsArr[index];
            log("Removing " + itemName + " from inventory...");
            removed = inventory.remove(itemName, candidate);
            interactions.remove(candidate);
        }
      return removed;
    }


    public List<Portable> removeAll(Portable item) {
        return inventory.removeAll(item.name());
    }

    public List<Portable> removeAll(String itemName) {
        return inventory.removeAll(itemName);
    }

    public boolean contains(Portable item) {
        return inventory.containsValue(item);
    }

    @Override
    public boolean contains(String itemName) {
        return manager.contains(itemName);
    }

    public int howMany(String itemName) {
        List<Portable> items = inventory.get(itemName);
        return items.size();
    }

    public int size() {
        return inventory.size();
    }

    public boolean isEmpty() {
        if (inventory.size() == 0) {
            print("There is nothing to interact with in your inventory.");
            return true;
        }
      return false;
    }

    public String toString() {
        int count = 0;
        String inventoryContents = "Inventory contents: ";
        if (!inventory.isEmpty()) {
            for (String itemName : inventory.keySet()) {
                count = inventory.get(itemName).size();
                String addDescription= "\n" + itemName + " (" + count + ")";
                inventoryContents += addDescription;
            }
        } else inventoryContents += "\n" + "(Empty)";
      return inventoryContents;
    }

    public void printInventory() {
        int count = 0;

        if(interactions.size() > 0) {
            List<String> list = new ArrayList<String>();
            for (String thing : inventory.keySet()) {
              count = inventory.get(thing).size();
              String addDescription= thing + " (" + count + ")";

              list.add(addDescription);
          }
          wordWrapPrint(GrammarGuy.oxfordCommify(list));
        }
    }

    @Override
    public void removeActor(Interacter actor) {
        try {
            Portable item = (Portable)actor;
            remove(item);
        } catch (ClassCastException cce) {
            log("This is not a valid actor to remove from inventory!");
        }
    }


    @Override
    public void cleanupActors() {
        // TODO Auto-generated method stub
        for (Interacter actor : interactions) {
            if (actor.isDone(this)) removeActor(actor);
        }
    }

    @Override
    public boolean isBarren() {
        // TODO Auto-generated method stub
        return interactions.size() == 0 ? true : false;
    }

    @Override
    public String getName() { return "your inventory"; }

    @Override
    public PriorityQueue<Event> getCurrentEvents() {
        // TODO Auto-generated method stub
        return manager.getCurrentEvents();
    }

    @Override
    public PriorityQueue<Event> getCurrentEvents(Commands trigger) {
        // TODO Auto-generated method stub
        return manager.getCurrentEvents(trigger);
    }

    @Override
    public PriorityQueue<Event> getCurrentEvents(Commands trigger,
            String objectName) {
        // TODO Auto-generated method stub
        log("testing");
        return manager.getCurrentEvents(trigger, objectName);
    }

    @Override
    public List<Interacter> getInteracters() { return interactions; };
}
