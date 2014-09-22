package game.core.inputs;

import util.Paired;
import game.content.encounters.EncounterGenerator;
import game.core.events.Events;
import game.core.events.Priority;
import game.core.events.Theatres;
import game.core.interfaces.Stage;
import game.core.maze.Maze;
import game.core.maze.Win;
import game.core.maze.Maze.Room;
import game.core.positional.Cardinals;
import game.core.positional.Coordinate;
import game.objects.units.Player;
import game.player.util.Statistics;
import static util.TextParser.ParsedCommand;
import static util.TextParser.parseCommand;
import static util.Print.*;
import static game.core.inputs.Commands.*;
import static util.Utilities.*;
import static util.Loggers.*;

public final class GameInputHandler {
    private GameInputHandler() { } //no instantiation

    public static void run(String input, Player you, Maze maze) {
        input = input.toLowerCase().trim();
        ParsedCommand cmd = parseCommand(input);
        String leadInput = cmd.command;
        String arg = cmd.object;
        String prep = cmd.preposition;
        String secondObject = cmd.secondObject;
            log("Lead Input: " + leadInput, Priority.LOW);
            log("Argument: " + arg, Priority.LOW);
            log("Preposition: " + prep, Priority.LOW);
            log("Second Object: " + secondObject, Priority.LOW);

        String[] inputs = input.split("\\s+");

        Commands fullCmd = get(input); //entire trimmed input, for checking intransitive actions
        Commands leadCmd = get(leadInput); //first word, delimited by space, for checking transitive actions (that allow or require arguments)


        if (check(leadCmd).in(APPROACH, TALK)) {
            if (arg == null) {
                if (check(leadCmd).in(TALK)) {
                    print("\nYou talk to yourself.  You find the conversation unstimulating.");
                }
                else {
                    print("What do you want to " + leadCmd + "?");
                }
            }
            else {
              performAction(you, leadCmd, arg, you.getRoom());
            }
        }
        else if (leadCmd == FIGHT) {
            if (arg == null || arg.equals("all")) {
                performAction(you, leadCmd, you.getRoom());
            } else {
                performAction(you, leadCmd, arg, you.getRoom());
            }
        }
        else if (leadCmd == PICKUP) {
            if (arg == null || arg.equals("all")) {
                performAction(you, leadCmd, you.getRoom());
            }
            else {
                performAction(you, leadCmd, arg, you.getRoom());
            }
        }
        else if (leadCmd == USE) {
            if (arg == null) {
                print("What do you want to " + leadCmd + "?");
            } else if (prep != null) {
                if (secondObject == null) {
                    print("What do you want to " + leadCmd + " " + arg + " " + prep + "?");
                }
                else {
                    performAction(you, leadCmd, arg, prep, secondObject, you.inventory(),
                            you.getRoom());
                }
            } else {
                performAction(you, leadCmd, arg, you.inventory());
            }
        }
        else if (leadCmd == COMBINE) {
            if (arg == null) {
                print("What do you want to " + leadCmd + "?");
            } else if (prep != null) {
                if (secondObject == null) {
                    print("What do you want to " + leadCmd + " " + arg + " " + prep + "?");
                }
                else {
                    performAction(you, leadCmd, arg, prep, secondObject, you.inventory(), you.inventory());
                }
            }
        }
        else if (check(leadCmd).in(EQUIP, DROP, CONSUME)) {
            if (arg == null) {
                print("What do you want to " + leadCmd + "?");
            } else {
                performAction(you, leadCmd, arg, you.inventory());
            }
        }
        else if (leadCmd == UNEQUIP) {
            if (arg == null) {
                print("What do you want to " + leadCmd + "?");
            }
            else {
                performAction(you, leadCmd, arg, you.paperDoll());
            }
        }
        else if (leadCmd == DESCRIBE) {
            if (arg == null) {
                Room r = maze.getRoom(you.location());
                r.describeRoom();
            }
            else if (you.getRoom().contains(arg)){
                log("Room contains " + arg);
                performAction(you, leadCmd, arg, you.getRoom());
            }
            else if (you.inventory().contains(arg)){
                log("Inventory contains " + arg);
                performAction(you, leadCmd, arg, you.inventory());
            }
        }
        else if (leadCmd == MOVE) {
          if (arg == null) {
              print("Please supply a direction to move in.");
              you.narrator().respondToGo();
              }
          else {
            Cardinals direction = Cardinals.get(arg);
                    log("direction supplied was: " + direction, Priority.DORMANT); //debug
            //Coordinate oldLocation = you.location();

            if (direction == null) {
                print("That's not a valid direction! Try again. "
                        + "(for a list of valid directions, type: help move)");
            }
            else if (you.move(direction, maze)) { //returns true if the move is valid
                  print("You have moved " + direction + ".");

                  Room room = maze.getRoom(you.location());

                  if (you.inventory().contains("Enc-None")) {
                      print("\nRandom encounters begone! Enc-None shields you.\n");
                  } else {
                      EncounterGenerator.run(you);
                  }

                  Theatres.run(you, MOVE, room);

                  if(you.isAlive()) {
                    if (you.location().equals(maze.exit())) {
                      Win.foundExit();
                    } else {
                      you.narrator().talksAboutRoom(you, room);
                      if (!room.hasBeenVisited()) {
                        room.setVisitedTrue(you);
                        Statistics.globalUpdate("roomsExplored");
                      }
                      room.describeRoom();
                      you.whereCanIGo(maze);
                    }
                  }
                }
                else { //if you.move(direction) returned false
                    print("There's no path to the " + direction + " from this room.");
                }
          }
        }
        else if (leadCmd == RETURN) {
            Cardinals direction = Cardinals.get(you.location(), you.getPrevLocation());
            log("Current location: " + you.location() + "; Previous location: " + you.getPrevLocation());
            you.setLocation(you.getPrevLocation());
            print("You have moved " + direction + " and returned to the previous room.");
            Room room = maze.getRoom(you.location());
            Theatres.run(you, MOVE, room);

            if(you.isAlive()) {
                if (you.location().equals(maze.exit())) {
                  Win.foundExit();
                } else {
                  you.narrator().talksAboutRoom(you, room);
                  if (!room.hasBeenVisited()) {
                    room.setVisitedTrue(you);
                    Statistics.globalUpdate("roomsExplored");
                  }
                  room.describeRoom();
                  you.whereCanIGo(maze);
                }
            }
                    /*
            } else {
                print("You are unable to return to the previous room.");
            }*/
        }
        else if (leadCmd == ATTRIB) {
            you.printAttribs();
        }
        else if (leadCmd == STATS) {
            Statistics.globalPrintStats();
        }
        else if (leadCmd == HELP) {
            if (arg == null) print(HELP.getUsage());
            else {
                Commands helpCmd = get(arg);
                //prints error message if the command wasn't recognized.
                print(helpCmd.getUsage());
            }
        }
        else if (leadCmd == SYSTEM) {
            if (arg == null) print(SYSTEM.getUsage());
            else {
              if (inputs[1].equals("log")) {
                if (inputs.length == 2) print("higher // lower // current // priority");
                else if (inputs.length == 3 && inputs[2].equals("higher")) {
                  programLog.higher();
                  print("new program log level: " + programLog.getLevel());
                }
                else if (inputs.length == 3 && inputs[2].equals("lower")) {
                  programLog.lower();
                  print("new program log level: " + programLog.getLevel());
                }
                else if (inputs.length == 3 && inputs[2].equals("current")) {
                  print("current log level: " + programLog.getLevel());
                }
                else if (inputs.length == 3) {
                  Priority p = Priority.get(inputs[2]);
                  if (p != null) {
                    programLog.setLevel(p);
                    print("new program log level: " + programLog.getLevel());
                  }
                  else print("that priority level is not recognized.");
                }
              }
              else if (inputs[1].equals("exitloc")) {
                print("Exit location=> " + you.maze().exit());
              }
              else if (inputs[1].equals("centerloc")) {
                print("Center location=> " + you.maze().center());
              }
              else if (inputs[1].equals("astar")) {
                print("Astar shortest path=> " + you.maze().getAstarPathToExit());
              }
            }
        }
        else if(fullCmd == INVENTORY) {
            if(you.inventory().isEmpty()) { print("You have nothing in your inventory."); }
            else you.inventory().printout();
        }
        else if(fullCmd == EQUIPPED) {
            you.paperDoll().printout();
        }
        else if (fullCmd == WHEREGO) {
            you.whereCanIGo(maze);
        }
        else if (fullCmd == WHEREAM) {
            you.printLocation();
        }
        else if (fullCmd == MYSTATUS) {
            you.printStatusList();
        }
        else if(fullCmd == EXITMAZE) {
            you.getPath().add(you.location());
            print("\nThank you for playing in the Maze.");
        }
        else if (fullCmd == DEATH) {
            print("You disembowel yourself.");
            you.death();
        }
        else if (leadCmd == TELEPORT) {
            Coordinate teleportCoord = null;
            Integer coord1 = Integer.parseInt(inputs[1]);
            Integer coord2 = Integer.parseInt(inputs[2]);
            teleportCoord = new Coordinate(coord1,coord2);

            maze.map().checkLegalArgs(teleportCoord);

            you.setLocation(teleportCoord);
        }
        else if(fullCmd == COMMANDS) {

            /*
            print("\nList of commands:");
            int size = printList().length;

            int cols = 3;
            int rows = 0;
            int index = 0;

            do {
                rows++;
                size -= cols;
            } while (size % cols > 0);

            for (int i = 0; i < cols; i++) {
                printnb("--------------------------------------------");
                //print("|                  |");
            }
            print("");

            for (int i = 0; i < rows; i++) {

                print("");
                for (int j = 0; j < cols; j++) {
                    //printnb("|                  |");
                    printnb("| " + printList()[index] + " |");
                    index++;
                }
                print("");
                for (int j = 0; j < cols; j++) {
                    printnb("--------------------------------------------");
                }
                print("");
            }

            for(String str : printList()) print(str);
            */
            printList();
        }
        else print("\nThis instruction is invalid. Type 'commands' for a list of commands.");
    }

    public static boolean performAction(Player you, Commands action, String object, Stage stage) {
        //Boolean result = r.respondsTo(you, trigger, arg);
        Boolean result = Theatres.run(you, action, object, stage);
        String preposition = action.getPreposition() == null ? "" : action.getPreposition();
        //Grammar correction
        if (result == null) {
            print("There isn't a " + object + " to " + action + preposition + " in " +
                    stage.getName() + ".");
            return false;
        }
        else if (result == false) {
            print("You can't " + action + preposition + " the " + object + ".");
            return false;
        } else return true;
    }

    public static boolean performAction(Player you, Commands action, String object, String prep,
            String secondObject, Stage stage, Stage secondStage) {
        Boolean result = Theatres.run(you, action, object, prep, secondObject, stage, secondStage);
        if (result == null) {
            print("You can't do that.");
            return false;
        } else return true;
    }

    public static boolean performAction(Player you, Commands action, Stage stage) {
        Boolean result = Theatres.run(you, action, stage);
        if (result == false) {
            print("There is nothing to " + action + " here.");
        }
      return result;
    }
}
