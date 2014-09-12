package maze;

import static util.Print.print;

public class Win {
    private static boolean youWin = false;

    public static void foundExit() {
        print("You have found the exit to the maze! "
                + "You may exit this program at your convenience.");
        youWin = true;
    }

    public static boolean didI() { return youWin; }
}
