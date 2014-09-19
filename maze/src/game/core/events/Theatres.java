package game.core.events;

import static game.core.events.Priority.DORMANT;
import static game.core.events.Priority.LOW;
import static util.Loggers.log;
import static util.Print.print;
import static util.Utilities.checkNullArg;
import static util.Utilities.checkNullArgs;
import game.core.inputs.Commands;
import game.core.interfaces.Stage;
import game.objects.units.Player;

import java.util.Iterator;
import java.util.PriorityQueue;

public final class Theatres {
    private Theatres() { } //no instantiation

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

    public static Boolean run(Player player, Commands trigger, String object, String prep,
            String secondObject, Stage stage, Stage secondStage) {
        Boolean response = false;
        log("responding to..." + player + " " + trigger + " " + object + " " + prep + " " +
                secondObject + " in " + stage.getName(), LOW);

        if(!stage.contains(object)) {
            response = null;
            print(stage.getName() + " does not contain " + object);
        } else if (!stage.contains(secondObject)) {
            log(stage.getName() + " does not contain " + secondObject);

            //check if other stage (room) has second object
            if (!secondStage.contains(secondObject)) {
                log(secondStage.getName() + " also does not contain " + secondObject);
                print("There is no " + secondObject);
                response = null;
            } else {
                log(secondStage.getName() + " contains " + secondObject);
                stage.getCurrentEvents(trigger, object, prep, secondObject, secondStage);
                response = run(player, stage, Token.SINGLE);
            }
        } else {
            stage.getCurrentEvents(trigger, object, prep, secondObject);
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
}
