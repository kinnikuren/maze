package maze;

import static util.Print.print;

public class Win {
    private static boolean hasPlayerWon = false;

    public static void foundExit() {
        print("You have found the exit to the maze! "
                + "You may exit this program at your convenience.");
        hasPlayerWon = true;
    }

    public static boolean didI() { return hasPlayerWon; }
}
