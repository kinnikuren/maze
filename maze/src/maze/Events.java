package maze;

import static maze.Priority.*;
import static util.Loggers.*;
import static util.Print.print;
import static util.Utilities.checkNullArg;
import static util.Utilities.checkNullArgs;

import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import maze.Bestiary.Monster;

public final class Events {

    public enum Token {
        SINGLE,
        MULTIPLE
    }
    public enum EventActions {
        CLEAR_NON_STICKY,
        CLEAR_FORCED,
        ADD,
        DO_NOTHING;
    }

    public static class ResultMessage {
        private EventActions action;
        private Event event = null;

        public ResultMessage(EventActions action) {
            checkNullArg(action);
            this.action = action;
        }

        public ResultMessage(EventActions action, Event event) {
            checkNullArgs(action, event);
            this.action = action;
            this.event = event;
        }
    }

    public static class RunMessage {
        private final Player player;
        private final Commands trigger;
        private final Stage stage;
        private String object = null;
        private Token token = null;

        public RunMessage(Player player, Commands trigger, Stage stage) {
            checkNullArgs(player, trigger, stage);
            this.player = player;
            this.trigger = trigger;
            this.stage = stage;
        }

        public void setObject(String object) {
            checkNullArg(object);
            this.object = object;
        }

        public void setToken(Token token) {
            checkNullArg(token);
            this.token = token;
        }
    }

    public static Boolean run(RunMessage message) {
        Boolean response = false;

        Stage stage = message.stage;
        Player player = message.player;
        Commands trigger = message.trigger;
        String object = message.object;
        Token token = message.token != null ? message.token : Token.MULTIPLE;

        if(stage.isBarren()) {
            print("There is nothing to interact with here.");
        }
        else {
          if (object == null) {
            stage.getCurrentEvents(trigger);
            response = run(player, stage, token);
          }
          else {
            if(!stage.contains(object)) {
              response = null;
                    log(stage + " does not contain " + object);
            }
            else {
              stage.getCurrentEvents(trigger, object);
              response = run(player, stage, token);
            }
          }
        }
      return response;
    }

    public static Boolean run(Player player, Commands trigger, Stage stage) {
        boolean response = false;
        if(stage.isBarren()) {
            print("There is nothing to interact with here.");
        }
        else {
            stage.getCurrentEvents(trigger);
            response = run(player, stage, Token.MULTIPLE);
        }
      return response;
    }

    public static Boolean run(Player player, Commands trigger, String object, Stage stage) {
        Boolean response = false;
                log("responding to..." + player + " " + trigger + " " + object + " in " + stage, LOW);

        // if the stage (room/inventory) does not contain the object
        if(!stage.contains(object)) {
            response = null;
                    log(stage + " does not contain " + object);
        }
        else {
            stage.getCurrentEvents(trigger, object);
            response = run(player, stage, Token.SINGLE);
        }
      return response;
    }

    private static boolean run(Player player, Stage stage, Token token) {
        boolean response = false;
        PriorityQueue<Event> currentEvents = stage.getCurrentEvents(); //retrieves all events sitting in the queue
            log(currentEvents.toString(), LOW);
            log("Event Fire Counter = " + currentEvents.size(), LOW);

        if (currentEvents.size() > 0) {
          response = true;

          if (token == Token.MULTIPLE) {
            do {
              Event event = currentEvents.poll();
              ResultMessage rm = event.fire(player);
              event.cleanup(stage);

              processResultMessage(rm, currentEvents);

              if (currentEvents.size() == 0) {
                  log("Events queue has been cleared. Exiting event queue.");
              }

            } while (currentEvents.size() > 0);
          }
          else if (token == Token.SINGLE) {
            Event event = currentEvents.poll();      //polls ONLY the highest priority event (if multiple, picks one)
            ResultMessage rm = event.fire(player);   //and then clears the rest unless they are sticky!
            event.cleanup(stage);

            processResultMessage(rm, currentEvents);

                log("clearing event queue after firing singleton event.");
            clearNonSticky(currentEvents);
          }
        }
      return response;
    }

    public static void processResultMessage(ResultMessage rm, PriorityQueue<Event> currentEvents) {
        if (rm == null) { log("Process Result Message: result message is null", DORMANT); }
        else {
            switch(rm.action) {
            case CLEAR_NON_STICKY :
                clearNonSticky(currentEvents);
              break;
            case CLEAR_FORCED :
                clearForced(currentEvents);
              break;
            case ADD :
                if (rm.event != null) {
                    log("Process Result Message: adding event.");
                  currentEvents.add(rm.event);
                }
              break;
            case DO_NOTHING :
              break;
            }
        }
    }

    public static void clearNonSticky(PriorityQueue<Event> currentEvents) {
        /*
        for (Event e : currentEvents) {
          if (!e.isSticky())
            currentEvents.remove(e);
        }
        */

        for (Iterator<Event> itr = currentEvents.iterator(); itr.hasNext();) {
            Event i = itr.next();
            if (!i.isSticky()) {
                itr.remove();
            }
        }
    }

    public static void clearForced(PriorityQueue<Event> currentEvents) {
        currentEvents.clear();
    }

    public static Event announce(final Interacter talker, final Priority priority, final String message) {
        Event event = new Event(talker, priority) {
          @Override public ResultMessage fire(Player player) {
              print(message);
            return null;
          }
        };
      return event;
    }

    public static Event fight(final Monster enemy, final Priority priority) {
        Event event = new Event(enemy, priority) {
          @Override public ResultMessage fire(Player player) {
              InteractionHandler.run(enemy, player, new Module.Fight());
            return null;
          }
        };
      return event;
    }

    public static Event fightAll(List<Monster> monsterParty, final Interacter actor,
            final Priority priority) {
        Event event = new Event(monsterParty, actor, priority) {
          @Override public ResultMessage fire(Player player) {
              InteractionHandler.run(monsterParty, player, new Module.Fight());
              ResultMessage rm = new ResultMessage(EventActions.CLEAR_FORCED);
            return rm;
          }
        };
      return event;
    }

    public static Event fightSkeletonTwice(final Bestiary.Skeleton sk1, final Priority priority) {
        Event event = new Event(sk1, priority) {
          @Override public ResultMessage fire(Player player) {
              print(sk1.battlecry());
              InteractionHandler.run(sk1, player, new Module.Fight());

              log("Event Fire: post skeleton defeat, should now respawn!");

              final Monster sk2 = new Bestiary.Skeleton();
              final Priority p = MAX;
              Event reanimateEvent = new Event(sk2, p) {
                @Override public ResultMessage fire(Player player) {
                    print("The skeleton magically re-animates itself and ATTACKS YOU AGAIN!");
                    //print(sk2.battlecry());
                    InteractionHandler.run(sk2, player, new Module.Fight());
                  return null;
                }
              };
            Events.ResultMessage rm = new Events.ResultMessage(EventActions.ADD, reanimateEvent);
            return rm;
          }
        };
      return event;
    }

    public static Event question(final Questioner questioner, final Priority priority) {
        Event event = new Event(questioner, priority) {
          @Override public ResultMessage fire(Player player) {
            //InteractionHandler.run(questioner, player, new Module.Question());
              questioner.question(player);
            return null;
          }
        };
      return event;
    }

    public static Event pickup(final Portable item) {
        Event event = new Event(item, LOW) {
          @Override public ResultMessage fire(Player player) {
              player.addToInventory(item);
              item.pickedUp();
              print("You have picked up the " + item.name() + ".");
            return null;
          }
        };
      return event;
    }

    public static Event consume(final Consumable consumable) {
        Event event = new Event(consumable, LOW) {
          @Override public ResultMessage fire(Player player) {
              print("You have consumed the " + consumable + ".");
              consumable.consumedBy(player);
              player.deleteFromInventory(consumable);
            return null;
          }
        };
      return event;
    }

    public static Event equip(final Equippable item) {
        Event event = new Event(item, LOW) {
          @Override public ResultMessage fire(Player player) {
              if (player.equip(item)) {
                item.equipped();
                print("You have equipped the " + item + ".");
              }
            return null;
          }
        };
      return event;
    }

    public static Event unequip(final Equippable item) {
        Event event = new Event(item, LOW) {
          @Override public ResultMessage fire(Player player) {
              if (player.unequip(item)) {
                  item.unequipped();
                  print("You have unequipped the " + item + ".");
              }
            return null;
          }
        };
      return event;
    }

    public static Event use(final Useable useable) {
        Event event = new Event(useable, LOW) {
          @Override public ResultMessage fire(Player player) {
              print("You have used the " + useable + ".");
              useable.usedBy(player);
            return null;
          }
        };
      return event;
    }

    public static Event describe(final Portable item) {
        Event event = new Event(item, LOW) {
          @Override public ResultMessage fire(Player player) {
              print(item.details());
            return null;
          }
        };
      return event;
    }

    public static Event drop(final Portable item) {
        Event event = new Event(item, LOW) {
          @Override public ResultMessage fire(Player player) {
              player.getInventory().removeActor(item);
              item.dropped();
              player.getRoom().addActor(item);
            return null;
          }
        };
      return event;
    }
}
