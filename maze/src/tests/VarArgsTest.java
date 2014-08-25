package tests;

public class VarArgsTest {

    public static void printWithArrows(String... args) {
        for (String str : args) {
            System.out.print(str + " => ");
        }
    }
    public static void main(String[] args) {
        String s1 = "go";
        String s2 = "this";
        String s3 = "way";
        printWithArrows(s1, s2, s3);
    }

}
