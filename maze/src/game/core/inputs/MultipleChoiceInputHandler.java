package game.core.inputs;

import java.util.Scanner;
import static util.Print.*;

public class MultipleChoiceInputHandler {
    private static Scanner scanner = new Scanner(System.in);

    public static String run(int num) {
         String input = scanner.nextLine();
         boolean validInput = false;

         do {

             if (input.equals("A") || input.equals("a")) validInput = true;
             else if (input.equals("B") || input.equals("b")) validInput = true;
             else if (num > 2) {
                 if (input.equals("C") || input.equals("c")) validInput = true;
                 if (num > 3) {
                     if (input.equals("D") || input.equals("d")) validInput = true;
                 }
             }

             if (!validInput) {
                 print(">Invalid input. Try again.<");
                 input = scanner.nextLine();
             }
         } while(!validInput);


         return input.toUpperCase();
    }

}
