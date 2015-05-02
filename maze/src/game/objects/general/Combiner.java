package game.objects.general;

import java.util.*;

import com.google.common.collect.ArrayListMultimap;

import game.core.interfaces.*;
import game.objects.items.AbstractItem;
import game.objects.items.Trinkets.*;
import game.objects.items.Useables.*;
import game.objects.items.Weapons.*;
import util.Tuple;
import static util.CombineSolver.*;

public class Combiner {

    @SuppressWarnings("rawtypes")
    public static class RecipeItem {
        private Class c;
        public RecipeItem(Class c) {
            this.c = c;
        }
        public boolean matches(Object o) {
            return c.isInstance(o);
        }
        @Override
        public String toString() {
            return c.getName();
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

        RecipeItem[] tr = new RecipeItem[] {new RecipeItem(Flammable.class),
                new RecipeItem(Flammable.Flame.class), new RecipeItem(WoodenStick.class)};
        List<RecipeItem> torchRecipe = new ArrayList<RecipeItem>(Arrays.asList(tr));
        AbstractItem[] ar = new AbstractItem[] {new OilyRag(), new Match(), new WoodenStick()};
        List<AbstractItem> torchIngredients = new ArrayList<AbstractItem>(Arrays.asList(ar));

        Set<Tuple<RecipeItem, AbstractItem>> combos = new HashSet<Tuple<RecipeItem, AbstractItem>>();
        for (RecipeItem ri : torchRecipe) {
            for (AbstractItem ai : torchIngredients) {
                if (ri.matches(ai)) {
                    combos.add(new Tuple<RecipeItem, AbstractItem>(ri, ai));
                }
            }
        }
        System.out.println(combos);
        Set<Tuple<RecipeItem, AbstractItem>> comboSolution = findSolutionTuples(combos);
        boolean canBeSolved = doesCombineSolutionExist(combos);
        System.out.println(comboSolution);
        System.out.println("can be solved? " + canBeSolved);

    }
}
