package tests;

public class FibonacciTest {

    public static int fib(int itr) {
        Integer prev = 1;
        Integer curr = 0;

        for (int i=0; i<itr; i++) {
            //System.out.println("i " + i);
            if (i == 0) {
                curr = prev + 0;
            } else {
                curr = prev + curr;
                prev = curr - prev;
            }
        }
        return curr;
    }

    public static int fib2(int itr) {
        if (itr <= 1) {
            return 1;
        } else {
            return fib2(itr-1) + fib2(itr-2);
        }
    }

    public static void main(String[] args) {
        System.out.println(fib(2));
        System.out.println(fib2(2));
        System.out.println(fib(6));
        System.out.println(fib2(6));
        //0 1 -> 1 2 3 5 8 13

        System.out.println(fib(7));
        assert fib(3) > fib(2) : "failed fib3 > fib2";
    }

}
