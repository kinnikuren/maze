package util;

import java.util.*;

import static game.core.inputs.GrammarGuy.*;
import static util.Loggers.*;

public class TextParser {

    public static class ParsedCommand {
        public final String command;
        public final String object;
        public final String preposition;
        public final String secondObject;

        private ParsedCommand(String command, String object, String preposition, String secondObject) {
            this.command = command != null ? command.trim() : null;
            this.object = object != null ? object.trim() : null;
            this.preposition = preposition != null ? preposition.trim() : null;
            this.secondObject = secondObject != null ? secondObject.trim() : null;
        }
    }

    public static ParsedCommand parseCommand(String input) {
        String command = null;
        String object = null;
        String preposition = null;
        String secondObject = null;


        //String[] parsedCommands = new String[4];
        input = input.toLowerCase().trim();

        String[] inputs = input.split("\\s+");
        //log("Splitting input..");

        command = inputs[0];
        if (inputs.length > 1) {
            //arg = inputs[1];
            object = "";

            //for (int i = 1; i < inputs.length; i++) {
                //use for loop here
            //}

            /*
            do {
                prepFlag = isPreposition(inputs[i]);
                if(prepFlag) {
                    preposition = inputs[i];
                } else {
                    object += " " + inputs[i];
                }
                i++;
            } while(i < inputs.length && !prepFlag);
            */
            int index = 0;

            for (int i = 1; i < inputs.length; ++i) {
                //log(i + " " + inputs[i]);
                index = i;

                if (isPreposition(inputs[i])) {
                    preposition = inputs[i];
                    index++;

                    if (index < inputs.length) {
                        String tempObject = "";
                        while (index < inputs.length) {
                            tempObject += " " + inputs[index];
                            ++index;
                        }
                        //if (object == null) object = tempObject;
                        //else secondObject = tempObject;
                        secondObject = tempObject;
                    }
                    break;
                }
                else {
                    object += " " + inputs[i];
                }
            }

        }
        ParsedCommand parsedCmds = new ParsedCommand(command, object, preposition, secondObject);

      return parsedCmds;
    }
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
        //String leadInput = input.split("\\s+", numSpaces+1)[0];
        //print("Lead Input = " + leadInput);
        //print("Input = " + inputs);
        //String arg = null;
        //print("Arg = " + arg);

        //Commands fullCmd = get(input); //entire trimmed input, for checking intransitive actions
        //Commands leadCmd = get(leadInput);

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //String toParse = "use red potion on goblin goon";
        String toParse = "use red potion on goblin";
        ParsedCommand cmd = parseCommand(toParse);

        System.out.println("parsed command is => ");
        System.out.println("    command: " + cmd.command);
        System.out.println("    object: " + cmd.object);
        System.out.println("    preposition: " + cmd.preposition);
        System.out.println("    secondObject: " + cmd.secondObject);
    }

}
