package tests;
import static game.player.util.Attributes.DEX;
import static game.player.util.Attributes.INT;
import static game.player.util.Attributes.STR;
import game.core.interfaces.Equippable;
import game.objects.items.AbstractItemArmor;
import game.objects.items.AbstractItemEquippable;
import game.objects.items.Armor;
import game.objects.items.Armor.BrownFedora;
import game.objects.items.Armor.Helmet;
import game.objects.items.Armor.SuperSuit;
import game.objects.items.Armor.ExtraSuperSuit;


import java.util.*;

public class CombineTraversalTest {

    public static class TestItem {
        private Set<String> typeMatch =  new HashSet<String>();
        public TestItem(String[] types) {
            this.typeMatch.addAll(Arrays.asList(types));
        }
        public boolean match(String type) {
            return this.typeMatch.contains(type);
        }
    }

    public static class TestTypes {
        public Set<String> typeSet = new HashSet<String>();
        TestTypes(String[] types) {
            this.typeSet.addAll(Arrays.asList(types));
        }
    }

    public boolean isArmor(Object o) {
        Class aia = AbstractItemArmor.class;
        return aia.isInstance(o);
        //return o instanceof AbstractItemArmor;
        /* try { AbstractItemArmor a = (AbstractItemArmor)o;
              return true;
        } catch (ClassCastException cce) {
            return false;
        } */
    }

    public boolean isHelmet(Object o) {
        Class h = Helmet.class;
        return h.isInstance(o);
        //return o instanceof Helmet;
    }

    public boolean isEquippable(Object o) {
        Class eq = Equippable.class;
        return eq.isInstance(o);
        //return o.getClass().isInstance(eq);
    }

    public boolean isExtraSuperSuit(Object o) {
        Class ess = ExtraSuperSuit.class;
        return ess.isInstance(o);
    }

    public static void main(String[] args) {

        String[] types = new String[] {"A", "B", "C"};
        TestTypes tt = new TestTypes(types);

        Class armor = Armor.class;
        Class helmet = Helmet.class;
        BrownFedora bf = new BrownFedora();
        SuperSuit ss = new SuperSuit();
        //AbstractItemArmor ss2 = new SuperSuit();
        AbstractItemEquippable ss2 = new ExtraSuperSuit();

        CombineTraversalTest ctt = new CombineTraversalTest();
        if (ctt.isArmor(ss2)) System.out.println("Super Suit is Armor");
        if (ctt.isArmor(bf)) System.out.println("Brown Fedora is Armor");
        if (ctt.isHelmet(ss2)) System.out.println("Super Suit is Helmet");
        if (ctt.isHelmet(bf)) System.out.println("Brown Fedora is Helmet");
        System.out.println("--------------");
        if (ctt.isEquippable(ss2)) System.out.println("Super Suit is Equippable");
        if (ctt.isEquippable(bf)) System.out.println("Brown Fedora is Equippable");
        System.out.println("--------------");
        if (ctt.isExtraSuperSuit(ss2)) System.out.println("Super Suit is ExtraSuperSuit");


    }

}
