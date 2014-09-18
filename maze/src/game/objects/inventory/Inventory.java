package game.objects.inventory;

import game.core.events.Event;
import game.core.events.EventManager;
import game.core.events.Interacter;
import game.core.events.Stage;
import game.core.inputs.Commands;
import game.core.inputs.GrammarGuy;
import game.objects.items.Portable;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import static util.Loggers.*;
import util.NullArgumentException;
import util.View;

import com.google.common.collect.ArrayListMultimap;

import static util.Print.print;
import static util.Print.wordWrapPrint;
import static util.Utilities.*;

public class Inventory implements Stage {
    private ArrayListMultimap<String, Portable> inventory = ArrayListMultimap.create();
    private View<Portable> portables;
    EventManager manager;

    public Inventory() {
        this.portables = new View<Portable>(inventory.values());
        this.manager = new EventManager(inventory.values());
    }

    public View<Portable> viewAllInventory() {
        return portables;
     }

    //@throws[NullArgumentException]
    public void add(Portable item) throws NullArgumentException {
        checkNullArg(item);
        inventory.put(item.name(), item);
        log("Item added to inventory interactions: " + portables);
    }

    public Portable remove(Portable item) {
        String itemName = item.name();
        Portable removed = null;
        for (Portable i : inventory.get(itemName)) {
          if (i.equals(item)) removed = i;
        }
        if (removed != null) {
            inventory.remove(itemName, removed);
            log(removed + " removed from inventory interactions: " + portables);
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

        if(portables.size() > 0) {
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
        for (Portable item : portables) {
            if (item.equals(actor)) remove(item);
          }
    }


    @Override
    public void cleanupActors() {
        // TODO Auto-generated method stub
        for (Portable item : portables) {
            if (item.isDone(this)) removeActor(item);
        }
    }

    @Override
    public boolean isBarren() {
        // TODO Auto-generated method stub
        return (portables.size() == 0);
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

    public PriorityQueue<Event> getCurrentEvents(Commands trigger, String objectName, String prep,
            String secondObjectName) {
        return manager.getCurrentEvents(trigger, objectName, prep, secondObjectName);
    }
}
