package tests;

import java.util.ArrayList;
import java.util.List;

import util.View;

import com.google.common.base.Function;

public class SimpleWord {
    public enum Convert implements Function<SimpleWord, Palindrome> {
        TO_PALINDROME;
        @Override public Palindrome apply(SimpleWord w) {
            return new Palindrome(w);
        }
    }
    public enum FromString implements Function<String, SimpleWord> {
        DO;
        @Override public SimpleWord apply(String str) {
          return new SimpleWord(str);
        }
    }

    private String theWord = null;
    private List<SimpleWord> dummy = new ArrayList<SimpleWord>();

    public SimpleWord(String s) { this.theWord = s; }

    public String toString() {
        return theWord;
    }

    public View<Palindrome> viewDummyPalindromes() {
        return new View<Palindrome>(dummy, Convert.TO_PALINDROME);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        else if (o instanceof SimpleWord) return this.toString().equals(o.toString());
        else return this.toString().equals(o);
    }
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
