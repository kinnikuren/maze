package util;

import static game.core.events.Priority.HIGH;
import static util.Print.print;

import java.util.ArrayList;

import game.core.events.InteractionHandler;
import game.core.events.Module;
import game.core.events.Theatres.EventActions;
import game.core.events.Theatres.ResultMessage;
import game.objects.units.Bestiary.Monster;
import game.objects.units.Player;

public final class EventPs {

    private static final EventP<Monster> MULTIFIGHT =
        new EventP<Monster>(new ArrayList<Monster>(), HIGH) {
            @Override public ResultMessage fire(Player player) {
                InteractionHandler.run(this.actors(), player, new Module.Fight());
                this.actors().clear();
                ResultMessage rm = new ResultMessage(EventActions.CLEAR_FORCED);
              return rm;
            }
        };

    public static EventP<Monster> multiFight(final Monster enemy) {
        MULTIFIGHT.addActor(enemy);
      return MULTIFIGHT;
    }


}
