package game.objects.general;

import java.util.*;

import com.google.common.collect.ArrayListMultimap;

import game.core.interfaces.*;
import game.objects.items.AbstractItem;
import game.objects.items.AbstractItemWeapon;
import game.objects.items.Trinkets.*;
import game.objects.items.Useables.*;
import game.objects.items.Weapons.*;
import util.Tuple;
import static util.CombineSolver.*;

public class Combiner {

    @SuppressWarnings("rawtypes")
    public static class Ingredient {
        private Class c;
        public Ingredient(Class c) {
            this.c = c;
        }
        public boolean matches(Object o) {
            return c.isInstance(o);
        }
        @Override
        public String toString() {
            return "Ingredient: " + c.getSimpleName();
        }
    }

    @SuppressWarnings("rawtypes")
    public static class Combination {
        private List<Class> recipe = new ArrayList<Class>();

        private Combination() { }

        public static Combination createFromItemList(List<Portable> items) {
            Combination combo = new Combination();
            for (Portable item : items) {
              combo.recipe.add(item.getClass());
            }
          return combo;
        }

        public static Combination createFromClassList(
                List<Class<? extends Portable>> itemClasses) {
            Combination combo = new Combination();
            for (Class<? extends Portable> itemClass : itemClasses) {
              combo.recipe.add(itemClass);
            }
          return combo;
        }

        public List<Class> recipe() { return recipe; }

        public int size() { return recipe.size(); }

        @Override public String toString() { return recipe.toString(); }

        @SuppressWarnings("unchecked")
        public boolean accepts(Combination inputCombo) {
            boolean result = false;

            if (size() == inputCombo.size()) { //different sized recipes are not allowed to match
              ArrayListMultimap<Class, Class> matchMap = ArrayListMultimap.create();

              for (Class cOther : inputCombo.recipe()) {
                for (Class cThis : recipe) {
                  if (cThis.isAssignableFrom(cOther)) //check if class in this recipe can super() the checked input class
                    matchMap.put(cOther, cThis);
                }
              }

              if (matchMap.keySet().containsAll(inputCombo.recipe())) {
                if (matchMap.values().containsAll(recipe))
                  result = true; //in a valid match, all ingredients of the input recipe will map to at least one checked ingredient
              }                  // and all ingredients of the checked recipe will be mapped to by at least one input ingredient
            }
          return result;
        }

    }

    public static Portable combine(Portable... inputItems) {
        List<Portable> inputItemsList = Arrays.asList(inputItems);
      return Combinations.combine(inputItemsList);
    }

    public static Portable combine(List<Portable> inputItems) {
      return Combinations.combine(inputItems);
    }

    public enum Combinations {
        C01 (new Portable[] {new RedKey(), new BlueKey()})
        {
            @Override public Portable result() { return new PurpleKey(); }
        },
        C02(new Portable[] {new OilyRag(), new WoodenStick()})
        {
            @Override public Portable result() { return new UnlitTorch(); }
        },
        C03(new Portable[] {new UnlitTorch(), new Matches()})
        {
            @Override public Portable result() { return new LitTorch(); }
        };

        private final List<Portable> itemRecipe;
        private final Combination combination;

        Combinations(Portable[] inputItems) {
            List<Portable> items = Arrays.asList(inputItems);
            this.combination = Combination.createFromItemList(items);
            this.itemRecipe = Arrays.asList(inputItems);
        }

        public abstract Portable result();

        public String recipe() {
            return itemRecipe.toString();
        }

        public boolean accepts(Combination inputCombo) {
          return combination.accepts(inputCombo);
        }

        public static Portable combine(List<Portable> inputItems) {
            Portable result = null;

            Combination inputCombo = Combination.createFromItemList(inputItems);
            for (Combinations combo : Combinations.values()) {
              if (combo.accepts(inputCombo))
                result = combo.result();
            }
          return result;
        }

    }

    public static void main(String[] args) {
        //System.out.println(Combiner.Combinations.C01.recipe());
        //System.out.println(Combiner.Combinations.C02.result());
        //System.out.println(Combiner.Combinations.C03.result());

        /*Ingredient[] tr = new Ingredient[] {new Ingredient(Flammable.class),
                new Ingredient(Flammable.Flame.class), new Ingredient(WoodenStick.class)};*/
        Ingredient[] tr = new Ingredient[] {new Ingredient(Flammable.class),
                new Ingredient(Flammable.Flame.class), new Ingredient(WoodenStick.class), new Ingredient(Coin.class)};
        List<Ingredient> torchRecipe = new ArrayList<Ingredient>(Arrays.asList(tr));

        //AbstractItem[] ar = new AbstractItem[] {new OilyRag(), new Match(), new WoodenStick()};
        //AbstractItem[] ar = new AbstractItem[] {new OilyRag(), new EncNone(), new WoodenStick()};
        AbstractItem[] ar = new AbstractItem[] {new OilyRag(), new EncNone(), new WoodenStick(), new FlammableCoin()};
        List<AbstractItem> torchIngredients = new ArrayList<AbstractItem>(Arrays.asList(ar));

        Set<Tuple<Ingredient, AbstractItem>> combos = new HashSet<Tuple<Ingredient, AbstractItem>>();

        System.out.println("torchRecipe size " + torchRecipe.size());
        System.out.println("torchIngredients size " + torchIngredients.size());

        boolean matched = false;
        boolean matchedAll = true;
        for (Ingredient ri : torchRecipe) {
            matched = false;
            System.out.println("processing recipe item " + ri);

            for (AbstractItem ai : torchIngredients) {
                if (ri.matches(ai)) {
                    System.out.println("recipe item " + ri + " matches torch ingredient " + ai);
                    combos.add(new Tuple<Ingredient, AbstractItem>(ri, ai));
                    matched = true;
                }
            }
            if (!matched) {
                System.out.println("FAILED to find match for " + ri);
                matchedAll = false;
                break;
            }
        }

        if (matchedAll) {
            System.out.println(combos);
            Set<Tuple<Ingredient, AbstractItem>> comboSolution = findSolutionTuples(combos);
            boolean canBeSolved = isCombineSolutionValid(combos, comboSolution);
            System.out.println(comboSolution);
            System.out.println("can be solved? " + canBeSolved);
        }
        else System.out.println("Not all ingredients found matching items, so no solution is possible.");

    }
}
