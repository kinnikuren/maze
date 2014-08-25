package maze;

import java.util.*;

public class TextParser {

    public static String[] parseText(String input) {
        String command = null;
        String object = null;
        String preposition = null;
        String secondObject = null;

        String[] finalInput = new String[4];
        input = input.toLowerCase().trim();
        ArrayList<String> preps = new ArrayList<String>();
        preps.add("to");
        preps.add("on");
        preps.add("with");
        preps.add("from");

        boolean prepFlag = false;

        /*
        int numSpaces = 0;
        int prevSpacePos = 0;
        int nextSpacePos = 0;

        //finds number of spaces between words
        do {
            prevSpacePos = nextSpacePos;
            nextSpacePos = input.indexOf(' ',prevSpacePos+1);
            //print("Next space position: " + nextSpacePos);
            //print("Previous space position: " + prevSpacePos);
            if (nextSpacePos != -1 && (nextSpacePos-prevSpacePos != 1)) {
                numSpaces++;
            }
        } while(nextSpacePos != -1);
         */

        //print("Number of spaces: " + numSpaces);
        String[] inputs = input.split("\\s+");
        //String leadInput = input.split("\\s+", numSpaces+1)[0];
        //print("Lead Input = " + leadInput);
        //print("Input = " + inputs);
        //String arg = null;
        command = inputs[0];
        if (inputs.length > 1) {
            //arg = inputs[1];
            object = "";
            //for (int i = 1; i < inputs.length; i++) {
                //print(inputs[i]);
            int i = 1;
            do {
                if(preps.contains(inputs[i])) {
                    preposition = inputs[i];
                    prepFlag = true;
                } else {
                    object += inputs[i];
                }
                i++;
            } while(i < inputs.length && !prepFlag);
            //}

            if (i < inputs.length) {
                secondObject = "";
                while (i < inputs.length) {
                    secondObject += inputs[i];
                    i++;
                }
            }

        }
        //print("Arg = " + arg);

        //Commands fullCmd = get(input); //entire trimmed input, for checking intransitive actions
        //Commands leadCmd = get(leadInput);

        finalInput[0] = command;
        finalInput[1] = object;
        finalInput[2] = preposition;
        finalInput[3] = secondObject;

        return finalInput;
    }


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String[] input = parseText("use red potion on goblin goon");
        for (int i = 0; i < input.length; i++) {
            System.out.println(input[i]);
        }
    }


}
