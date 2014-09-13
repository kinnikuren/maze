package maze;

import java.util.ArrayList;
import java.util.List;

import static maze.Spells.*;

public class SpellBook {
    private List<Spell> spells = new ArrayList<Spell>();

    public SpellBook() {
        this.spells.add(new FireStorm());
    }

    public List<Spell> getSpells() { return spells; }

    public boolean contains(String spellName) {
        for (Spell s : spells) {
            if (s.toString().equalsIgnoreCase(spellName)) return true;
        }
        return false;
    }

    public Spell getSpell(String spellName) {
        for (Spell s : spells) {
            if (s.toString().equalsIgnoreCase(spellName)) return s;
        }
        return null;
    }
}
