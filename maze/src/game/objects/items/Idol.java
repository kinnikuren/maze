package game.objects.items;

import game.core.events.Event;
import game.core.events.Events;
import game.core.inputs.Commands;
import game.core.interfaces.Questioner;
import game.core.interfaces.Stage;
import game.core.positional.Coordinate;
import game.objects.units.AbstractUnit;
import game.objects.units.Player;

import java.util.*;

import static util.Print.*;
import static game.core.events.Priority.*;
import static game.core.inputs.Commands.*;
import static game.objects.general.References.*;

public class Idol extends AbstractItemFixture
implements Questioner {
    public static final int classId = IDOL.classId;

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
            String formattedAnswer = answer.toLowerCase();
            List<String> answerList = this.get(ques);
            if (answerList.contains(formattedAnswer)) return true;
            else return false;
        }
    }

    public Idol() {
        super();
        this.createRiddles();
    }

    //begin implementation of Actor methods
    @Override
    public boolean matches(String name) {
      return matchRef(IDOL, name);
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
            print("You hear a rumble from within the stone idol. An unexpected light "
                    + "flickers in its eyes.");
            selfPrint("Hello, " + player.name() + "...");
            selfPrint("You stand before the Great One. Are you wise..?");
            selfPrint("Answer well, fleshling, and you will have MY blessings.");
            int randQ = new Random().nextInt(3);
            int count = 3;
            String[] questions = riddles.getQuestions().toArray(new String[0]);
            //print("Number of questions: " + questions.length);
            String question = questions[randQ];
            Scanner scanner = new Scanner(System.in);

            selfPrint(question);
            while(count != 0) {
                String input = scanner.nextLine();

                if(riddles.checkAnswer(question, input)) break;
                else {
                    --count;
                    selfPrint("That is incorrect, fool! You have "
                            + count + " guesses left.");
                }
            }
            if(count == 0) {
                result = false;
                selfPrint("You have failed my test! Now feel MY wrath!!");
                visitedBy(player);
                player.loseHP(1);
            }
            else {
                result = true;
                selfPrint("You have earned MY favor, for now.");
                visitedBy(player);
                int[] rewards = {3,5,8};
                int randReward = new Random().nextInt(3);
                player.addHP(rewards[randReward]);
            }
        }
        else {
            selfPrint("I have nothing MORE for you, greedy fleshling. Begone!");
            result = null;
        }
      return result;
    }

    private void createRiddles() {
        riddles.put("Which creature has one voice and yet becomes four-footed, "
                + "then two-footed, then three-footed?",
                new String[] {"man", "men", "human", "human being"});
        riddles.put("There are two sisters: one gives birth to the other and she, "
                + "in turn, gives birth to the first. Who are they?",
                new String[] {"day and night", "night and day"});
        riddles.put("I am weightless and hold nothing. If you put me in something I make it all "
                + "the lighter, and if you fill me I cease to be. What am I?",
                new String[] {"a hole", "hole", "you are a hole"});
        riddles.put("They follow and lead,  but only as you pass. Dress yourself in black, and "
                + "they are blacker still. Always they flee light, though without it they "
                + "cannot be. Who are they?",
                new String[] {"shadows", "they are shadows"});
    }

    public void selfPrint(String input) { print(name + ">> " + input); }
}
