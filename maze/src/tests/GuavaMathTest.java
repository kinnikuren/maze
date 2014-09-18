package tests;

import game.core.maze.AbstractRoom;
import game.core.positional.Coordinates;

import com.google.common.math.*;

import static util.Print.*;

public class GuavaMathTest {

    public static void main(String[] args) {

        int i = 1500600700;
        long j = 2794366595L;

        int k = (int) (i + j);
        print("k: " + k);

        long l = (long)i + j;
        print("l: " + l);


        /* if(IntMath.isPowerOfTwo(4)) print("yes");
        else print("no");

        if(IntMath.isPowerOfTwo(5)) print("yes");
        else print("no"); */
    }

}
