package maze;

import static maze.Priority.*;
import static util.Loggers.*;
import static util.Print.print;

import java.util.PriorityQueue;

public final class Events {
    public static Event announce(final Interacter talker, final Priority priority, final String message) {
        Event event = new Event(talker, priority) {
          @Override public void fire(Player player) {
            print(message);
          }
        };
      return event;
    }

    public static Event fight(final Fighter fighter, final Priority priority) {
        Event event = new Event(fighter, priority) {
          @Override public void fire(Player player) {
            print(fighter.battlecry());
            InteractionHandler.run(fighter, player, new Module.Fight());
          }
        };
      return event;
    }

    public static Event question(final Questioner questioner, final Priority priority) {
        Event event = new Event(questioner, priority) {
          @Override public void fire(Player player) {
            InteractionHandler.run(questioner, player, new Module.Question());
          }
        };
      return event;
    }

    public static Event pickup(final Portable item) {
        Event event = new Event(item, LOW) {
          @Override public void fire(Player player) {
            player.addToInventory(item);
            item.pickedUp();
            print("You have picked up the " + item.name() + ".");
          }
        };
      return event;
    }

    public static Event consume(final Consumable consumable) {
        Event event = new Event(consumable, LOW) {
          @Override public void fire(Player player) {
            print("You have consumed the " + consumable + ".");
            consumable.consumedBy(player);
            player.deleteFromInventory(consumable);
            print("You have picked up the " + consumable.name() + ".");
          }
        };
      return event;
    }

    public static Event equip(final Equippable item) {
        Event event = new Event(item, LOW) {
            @Override public void fire(Player player) {
                if (player.equip(item)) {
                    item.equipped();
                    print("You have equipped the " + item);
                }
            }
        };
      return event;
    }

    public static Event drop(final Portable item) {
        Event event = new Event(item, LOW) {
          @Override public void fire(Player player) {
            player.getInventory().removeActor(item);
            item.dropped();
            player.getRoom().addActor(item);
          }
        };
      return event;
    }

    public static boolean fire(Player player, Commands trigger, Stage stage) {
        boolean response = false;
        if(stage.isBarren()) {
            print("There is nothing to interact with here.");
        }
        else {
            stage.getCurrentEvents(trigger);
            response = fire(player, stage);
        }
      return response;
    }

    public static Boolean fire(Player player, Commands trigger, String object, Stage stage) {
        Boolean response = false;
            log("responding to..." + player + " " + trigger + " " + object, LOW);

        // if the stage (room/inventory) does not contain the object
        if(!stage.contains(object)) {
            response = null;
        }
        else {
            stage.getCurrentEvents(trigger, object);
            response = fire(player, stage);
        }
      return response;
    }

    public static boolean fire(Player player, Stage stage) {
        boolean response = false;
        PriorityQueue<Event> currentEvents = stage.getCurrentEvents();
            log(currentEvents.toString(), LOW);

        int counter = currentEvents.size();
        log("Counter = " + counter);
        if (counter > 0) response = true;

        for (int i = 0; i < counter; ++i) {
          Event event = currentEvents.poll();
          event.fire(player);
          event.cleanup(stage);
        }
      return response;
    }
}
