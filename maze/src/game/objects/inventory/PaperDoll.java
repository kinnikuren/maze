package game.objects.inventory;

import static util.Loggers.log;
import static util.Print.print;
import static util.Utilities.checkNullArg;
import static game.objects.inventory.PaperDoll.EquipSlots.*;
import game.core.events.Event;
import game.core.events.EventManager;
import game.core.events.Interacter;
import game.core.events.Stage;
import game.core.inputs.Commands;
import game.objects.items.Equippable;

import java.util.HashMap;
import java.util.PriorityQueue;

import util.NullArgumentException;
import util.View;


public class PaperDoll implements Stage {

    public enum EquipSlots {
        MAIN_HAND("main hand"),
        CHEST("chest"),
        HEAD("head");
        private final String name;
        EquipSlots(String name) { this.name = name; }
        public String toString() { return name; }
    }

    private HashMap<EquipSlots,Equippable> paperDoll = new HashMap<EquipSlots,Equippable>();
    private View<Equippable> equipments;
    EventManager manager;

    public PaperDoll() {
        for (EquipSlots slot : EquipSlots.values()) {
            this.paperDoll.put(slot, null);
        }
        this.equipments = new View<Equippable>(paperDoll.values());
        this.manager = new EventManager(paperDoll.values());
    }

    public View<Equippable> viewAllEquipped() {
      return equipments;
    }

    public Equippable getWeapon() {
      return paperDoll.get(MAIN_HAND);
    }

    public Equippable getHelmet() {
      return paperDoll.get(HEAD);
    }

    public Equippable getChestArmor() {
      return paperDoll.get(CHEST);
    }

    public Equippable getEquippedForSlot(EquipSlots slot) {
      return paperDoll.get(slot);
    }

  //@throws[NullArgumentException]
    public Equippable add(Equippable item) throws NullArgumentException {
        checkNullArg(item);

                log("Attempting to add " + item + " to paper doll...");
        Equippable oldItem = null;

        //oldItem = paperDoll.replace(item.type(), item);
        if (paperDoll.keySet().contains(item.type())) {
            oldItem = paperDoll.put(item.type(), item);
        }
                log("Item added to paperdoll: " + equipments);
        if (oldItem != null) log("Previous item removed from paperdoll: " + equipments);
      return oldItem;
    }

    public Equippable remove(Equippable item) {
        if (contains(item)) {
            paperDoll.put(item.type(), null);
            log(item + " removed from paperdoll: " + equipments);
          return item;
        }
      else return null;
    }

    public boolean contains(Equippable item) {
        log("Checking if paperdoll contains " + item + "...");
      return item != null ? paperDoll.containsValue(item) : false;
    }

    @Override
    public boolean contains(String itemName) {
      return manager.contains(itemName);
    }

    public void printout() { print(this); }

    public String toString() {
        String inventoryContents = "Equipment Loadout: ";
        for (EquipSlots slot : paperDoll.keySet()) {
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
        for (Equippable item : equipments) {
          if (item.equals(actor)) remove(item);
        }
    }


    @Override
    public void cleanupActors() {
        for (Equippable item : equipments) {
          if (item.isDone(this)) remove(item);
        }
    }

    @Override
    public boolean isBarren() {
      return (manager.interactions().size() == 0);
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

    @Override
    public PriorityQueue<Event> getCurrentEvents(Commands trigger, String objectName, String prep,
            String secondObjectName) {
        return manager.getCurrentEvents(trigger, objectName, prep, secondObjectName);
    }
}
