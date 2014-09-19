package game.core.events;

import static game.core.events.Priority.*;
import static util.Loggers.*;
import static util.Print.print;
import static util.Utilities.checkNullArg;
import static util.Utilities.checkNullArgs;
import game.core.events.Module.Fight;
import game.core.events.Theatres.EventActions;
import game.core.events.Theatres.ResultMessage;
import game.core.inputs.Commands;
import game.core.inputs.GrammarGuy;
import game.core.interfaces.Consumable;
import game.core.interfaces.Equippable;
import game.core.interfaces.Actor;
import game.core.interfaces.Portable;
import game.core.interfaces.Questioner;
import game.core.interfaces.Stage;
import game.core.interfaces.Useable;
import game.objects.general.Combiner;
import game.objects.units.Bestiary;
import game.objects.units.Player;
import game.objects.units.Bestiary.Monster;
import game.objects.units.Bestiary.Skeleton;
import game.core.maze.MazeMap.Gate;

import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public final class Events {

    //private static final Event = new Event()

    public static Event announce(final Actor talker, final Priority priority, final String message) {
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

    public static Event fightAll(final List<Monster> monsterParty,
            final Priority priority) {
        Event event = new Event(monsterParty, priority) {
          @Override public ResultMessage fire(Player player) {
              InteractionHandler.run(monsterParty, player, new Module.Fight());
              Bestiary.monsterParty.clear();
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
              ResultMessage rm = new ResultMessage(EventActions.ADD, reanimateEvent);
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
              player.getRoom().removeActor(item);
              player.addToInventory(item);
              print("You have picked up the " + item.name() + ".");
            return null;
          }
        };
      return event;
    }

    public static Event drop(final Portable item) {
        Event event = new Event(item, LOW) {
          @Override public ResultMessage fire(Player player) {
              player.inventory().removeActor(item);
              player.getRoom().addActor(item);
              print("You have dropped the " + item.name() + ".");
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
              if (player.unequip(item) != null) {
                  print("You have unequipped the " + item + ".");
              } else print("You don't have " + item  + " equipped.");
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

    public static Event describe(final Gate gate) {
        Event event = new Event(gate, LOW) {
          @Override public ResultMessage fire(Player player) {
              print(gate.inspect());
            return null;
          }
        };
      return event;
    }

    public static Event combine(final Portable firstItem, final Portable secondItem) {
        Event event = new Event(firstItem, LOW) {
            @Override public ResultMessage fire(Player player) {
                Portable newItem = Combiner.combine(firstItem, secondItem);
                if (newItem != null) {
                    //player.inventory().removeActor(firstItem);
                    //player.inventory().removeActor(secondItem);
                    player.inventory().remove(firstItem);
                    player.inventory().remove(secondItem);
                    player.inventory().add(newItem);
                    print("You have combined the " + firstItem.name() + " and the " +
                            secondItem.name() +
                            " to make " + GrammarGuy.addArticle(newItem.name()) + ".");
                } else {
                    print("You cannot combine " + firstItem.name() + " and " + secondItem.name()
                            + ".");
                }
                return null;
            }
        };
        return event;
    }

    public static Event useOn(final Useable item, final Actor target, final Stage secondStage) {
        Event event = new Event(item, LOW) {
            @Override public ResultMessage fire(Player player) {
                log("You use " + item + " on " + target + ".");
                if (item.usedBy(player, target, secondStage)) {
                }
                return null;
            }
        };
        return event;
    }
}
