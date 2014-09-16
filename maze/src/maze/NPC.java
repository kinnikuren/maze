package maze;

import java.util.HashMap;
import static maze.Armor.*;
import static util.Print.*;

public final class NPC {

    public static class Vendor extends AbstractUnit {
        private Inventory inventory = new Inventory();
        private HashMap<Portable, int[]> stock = new HashMap<Portable, int[]>();

        public Vendor() {
            this.inventory.add(new SuperSuit());
            this.stock.put(new SuperSuit(), new int[]{1, 100});
        }

        @Override
        public void defineTypeDefaultValues() {
            // TODO Auto-generated method stub
        }

        public void printStock() {
            print("Here's my stock:");
            for (Portable p : stock.keySet()) {
                print("Item: " + p + "; Quantity: " + stock.get(p)[0] + "; Cost: " +
                        stock.get(p)[1] + " bronze coins");
            }
        }

        public void removeItem(Portable item) {
            int x = stock.get(item)[0];
            print("Previous quantity: " + x);
            stock.put(item, new int[]{(x-1), 100});
            inventory.remove(item);
        }
    }

    public static void main(String[] args) {
        Vendor v = new Vendor();

        v.printStock();

        for (Portable p : v.stock.keySet()) {
            v.removeItem(p);
        }
        //Portable p = (Portable)v.inventory.interactions.get(0);
        //v.removeItem(p);
        v.printStock();
    }
}
