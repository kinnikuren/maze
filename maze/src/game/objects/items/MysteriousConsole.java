package game.objects.items;

import game.core.events.Event;
import game.core.events.Events;
import game.core.events.Questioner;
import game.core.events.Stage;
import game.core.inputs.Commands;
import game.core.inputs.MultipleChoiceInputHandler;
import game.core.positional.Coordinate;
import game.objects.units.AbstractUnit;
import game.objects.units.Player;

import java.util.*;

import static util.Print.*;
import static util.Loggers.*;
import static game.core.events.Priority.*;
import static game.core.inputs.Commands.*;
import static game.objects.general.References.*;

public class MysteriousConsole extends AbstractItemFixture
implements Questioner {
    public static final int classId = CONSOLE.classId;

    private Riddles riddles = new Riddles();
    //inner class for riddle creation and usage
    class Riddles {
        private HashMap<String,List<String>> riddleList = new HashMap<String,List<String>>();

        private void put(String ques, String[] answers) {
            riddleList.put(ques, Arrays.asList(answers));
        }
        private List<String> get(String ques) { return riddleList.get(ques); }

        private Set<String> getQuestions() { return riddleList.keySet(); }

        private boolean checkAnswer(String ques, String answer) {
            String formattedAnswer = answer.toUpperCase();
            List<String> answerList = this.get(ques);
            if (answerList.contains(formattedAnswer)) return true;
            else return false;
        }
    }

    public MysteriousConsole() {
        super();
        this.createRiddles();
    }

    public MysteriousConsole(Coordinate c) {
        super(c);
        this.createRiddles();
    }

    @Override
    public String name() { return "Mysterious Console"; }

    //begin implementation of Interacter methods
    @Override
    public boolean matches(String name) {
      return matchRef(CONSOLE, name);
    }
    @Override
    public boolean canInteract(AbstractUnit unit) {
      return unit instanceof Player ? true : false;
    }
    @Override
    public Event interact(Commands trigger) {
      return trigger == APPROACH ? Events.question(this, LOW) : null;
    }
    @Override
    public boolean isDone(Stage stage) { return false; }

    //begin other methods
    @Override
    public Boolean question(Player player) {
        Boolean result = false;

        if(!isVisited) {
            wordWrapPrint("The mysterious console is equipped with a keypad. The keypad only has "
                    + "eight "
                    + "keys - A B D L R S U. As you get closer, the console emits a series of "
                    + "electronic bleeps and bloops. The display lights up and words appear.");
            //selfPrint("Enter the code:");

            boolean coinInserted = false;
            boolean isCorrect = false;

            int count = 3;

            String[] questions = riddles.getQuestions().toArray(new String[0]);
            //print("Number of questions: " + questions.length);
            String question = questions[0];
            Scanner scanner = new Scanner(System.in);

            do {
                selfPrint("(Insert gold coin to play)");
                print("A) Insert a gold coin.");
                print("B) Leave.");
                coinInserted = false;

                String input = MultipleChoiceInputHandler.run(2);
                log("input entered was " + input);

                if (input.equals("A")) {
                    log("checking for coins...");
                    if (player.inventory().contains("gold coin")) {
                        log("player has a coin, now removing...");
                        player.inventory().remove("gold coin");
                        coinInserted = true;

                        selfPrint(question);

                        input = scanner.nextLine();

                        isCorrect = riddles.checkAnswer(question, input);

                        if (isCorrect) {
                            result = true;
                            selfPrint("You win!");
                            selfPrint("You have won the Warp Whistle!");
                            selfPrint("Thanks for playing!");

                            print("A plain whistle pops out of the console and you put it in "
                                    + "your pocket.");

                            player.inventory().add(new Useables.WarpWhistle());

                            visitedBy(player);
                        } else {
                            selfPrint("Incorrect. Try again.");
                        }
                    } else {
                        print("You do not have any gold coins.");
                    }
                } else if (input.equals("B")) break;

            } while(!isCorrect);

                /*
            if(count == 0) {
                result = false;
                selfPrint("Game Over.");
                visitedBy(player);
            }

            else {
                result = true;
                selfPrint("You win!");
                print("Three bronze coins pop out of the console, which you promptly pick up.");
                visitedBy(player);
                player.getInventory().add(new Trinkets.BronzeCoin());
                player.getInventory().add(new Trinkets.BronzeCoin());
                player.getInventory().add(new Trinkets.BronzeCoin());
            }
            */
        }
        else {
            print("The console is darkened and unresponsive.");
            result = null;
        }
      return result;
    }

    private void createRiddles() {
        riddles.put("Enter the code:",
                new String[] {"UUDDLRLRBAS"});
    }

    public void selfPrint(String input) { print(name() + " >> " + input); }
}
