package game.objects.general;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import game.core.interfaces.Portable;
import game.objects.items.Trinkets.*;
import game.objects.items.Useables.*;
import game.objects.items.Weapons.*;

public class Combiner {

    @SuppressWarnings("rawtypes")
    public static class Combination {
        private List<Class> recipe = new ArrayList<Class>();

        public Combination(List<Portable> items) {
            for (Portable item : items) {
                this.recipe.add(item.getClass());
            }
        }

        public List<Class> recipe() { return recipe; }

        public int size() { return recipe.size(); }

        @SuppressWarnings("unchecked")
        public boolean accepts(Combination inputCombo) {
            if (size() != inputCombo.size()) return false; //different sized recipes are not allowed to match
            else {
              List<Class> checkRecipe = new ArrayList<Class>(recipe); //temporary copy of recipe

              matching_loop: //loops through the classes in the inputted recipe
              for (Class cOther : inputCombo.recipe()) {
                boolean matched = false; //re-set to false for each input

                inner_loop:
                for (Class cThis : checkRecipe) {
                  if (cThis.isAssignableFrom(cOther)) { //check if a class in this recipe can super() the checked input class
                    matched = true;
                    checkRecipe.remove(cThis); //remove class from the temprecipe if matched, as it's already matched to an input class
                    break inner_loop; //stop inner loop when matched and move on to the next input class
                  }

                  if (!matched) break matching_loop; //don't bother completing the outer loop if an input class can't find any matches
                }
              }

              return (checkRecipe.size() == 0); //if everything matched, then everything in the temp recipe would be removed
            }
        }

    }

    public static Portable combine(Portable firstItem, Portable secondItem) {
        if ((firstItem instanceof RedKey && secondItem instanceof BlueKey) ||
                (firstItem instanceof BlueKey && secondItem instanceof RedKey)) {
            return new PurpleKey();
        } else if ((firstItem instanceof OilyRag && secondItem instanceof WoodenStick ) ||
                (firstItem instanceof WoodenStick && secondItem instanceof OilyRag)) {
            return new UnlitTorch();
        } else if ((firstItem instanceof UnlitTorch && secondItem instanceof Matches ) ||
                (firstItem instanceof Matches && secondItem instanceof UnlitTorch)) {
            return new LitTorch();
        } else return null;
    }

    public static void combine(List<Portable> items) {
        Class[] keyCombine1 = new Class[] {RedKey.class, BlueKey.class};

        int size = items.size();

        for (int i = 0; i < size; ++i) {
            for (Class c : keyCombine1) {
                if (c.isInstance(items.get(i))) { }
            }
        }
    }

    public enum Combinations {
        C01 (new Portable[] {new RedKey(), new BlueKey()}, new PurpleKey()
        ),
        C02(new Portable[] {new OilyRag(), new WoodenStick()}, new UnlitTorch()
        ),
        C03(new Portable[] {new UnlitTorch(), new Matches()}, new LitTorch()
        );

        private final Combination combination;
        private final Portable result;

        Combinations(Portable[] inputItems, Portable result) {
            List<Portable> items = Arrays.asList(inputItems);
            this.combination = new Combination(items);
            this.result = result;
        }

        public boolean matches(Combination inputCombo) {
          return combination.accepts(inputCombo);
        }

        public static Portable combine(List<Portable> inputItems) {
            Portable result = null;

            Combination inputCombo = new Combination(inputItems);
            for (Combinations combo : Combinations.values()) {
              if (combo.matches(inputCombo))
                result = combo.result;
            }
          return result;
        }

    }

}
