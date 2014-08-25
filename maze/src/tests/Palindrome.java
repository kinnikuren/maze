package tests;


public class Palindrome {
    private String palindrome = null;

    public Palindrome() { this.palindrome = "I have no word."; }

    public Palindrome(SimpleWord sw) { this.palindrome = sw.toString(); }

    public Palindrome(String s) { this.palindrome = s; }

    public String toString() {
        String emordnilap = new StringBuffer(palindrome).reverse().toString();
        return palindrome + emordnilap;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        else if (o instanceof Palindrome) return this.toString().equals(o.toString());
        else return this.toString().equals(o);
    }
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
