package game.core.events;

import game.core.events.Module.Fight;
import game.core.events.Module.Question;
import game.core.inputs.GameInputHandler;
import game.core.inputs.GrammarGuy;
import game.objects.general.Spells;
import game.objects.general.Spells.Spell;
import game.objects.units.Bestiary;
import game.objects.units.Player;
import game.objects.units.Bestiary.Monster;
import game.objects.units.Bestiary.Rat;
import game.player.util.Statistics;

import java.util.*;

import util.TextParser.ParsedCommand;
import static util.Print.*;
import static util.TextParser.parseCommand;
import static game.core.inputs.Commands.*;
import static game.objects.general.References.*;
import static game.player.util.Attributes.*;

public final class InteractionHandler {
    private static boolean playerTurn;
    private static boolean enemyTurn;
    private static boolean combatDone;
    private static Scanner scanner = new Scanner(System.in);
    private static String input;
    private static Random rand = new Random();

    private static String[] moves = {"attack (a) <<enemy>>", "magic (m)", "cast (c) <<spell>>",
            "flee (f)",
            "inventory (i)",
            "use (u) <<item>>", "consume (c) <<item>>",
            "kill (k) <<enemy>", "kill all (ka)"};

    private InteractionHandler() { } //no instantiation

    public static void run(Monster enemy, Player player, Module.Fight ft) {

        //oldCombatEngine(enemy, player);

        setUpFight();

        print("Starting fight!!...");
        print(enemy.battlecry());
        enemy.buff();

        List<Monster> singleEnemy = new ArrayList<Monster>();
        singleEnemy.add(enemy);

        while(enemy.hp() > 0 && player.hp() > 0 && !combatDone) {
            print("Your HP: "+player.hp());
            printnb("Enemy: ");
            printnb("[1]" + " " + enemy.name() + " (" + enemy.hp() + "/"
                        + enemy.getDefaultHP() + ") ");

            while (playerTurn && !combatDone) {
                playerGo(singleEnemy, player);
            }

            enemyTurn = true;

            if (!combatDone && enemy.isAlive()) {
                while (enemyTurn && player.isAlive()) {
                    enemyGo(enemy, player);

                    enemyTurn = false;
                }
            }
            //enemyCleanUp(enemy, player);
            playerTurn = true;
        }

        checkPlayerDeath(player);
    }

    public static boolean run(List<Monster> enemies, Player player, Module.Fight ft) {

        //oldCombatEngine(enemy, player);

        setUpFight();

        print("Starting fight!!...");

        HashMap<Integer, Monster> monsterMap = new HashMap<Integer, Monster>();
        int key=1;
        for (Monster m: enemies) {
            monsterMap.put(key, m);
            m.buff();
            key++;
        }
        //print(monsterMap);

        while(enemies.size()>0 && player.hp() > 0 && !combatDone) {
            print("Your HP: "+player.hp());
            printnb("Enemies: ");

            for (int i=0; i<enemies.size();i++) {
                Monster m = enemies.get(i);
                printnb("[" + (i+1) + "]" + " " + m.name() + " (" + m.hp() + "/"
                            + m.getDefaultHP() + ") ");
            }

            /*
            for (int i=1; i< monsterMap.size()+1; i++) {
                Monster m = monsterMap.get(i);
                printnb("[" + i + "]" + " " + m.name() + " (" + m.hp() + "/" + m.defaultHitPoints
                        + ") ");
            }
            */

            /*
            List<String> enemyNames = new ArrayList<String>();
            for (Monster m : enemies) enemyNames.add(m.name());
            print(GrammarGuy.oxfordCommify(enemyNames));
            */

            //Monster enemy = enemies.get(rand.nextInt(enemies.size()));

            while (playerTurn && !combatDone) {

                //attack random monster in a group
                playerGo(enemies, player);

            }
            enemyTurn = true;

            if (!combatDone && enemies.size()>0) {
                while (enemyTurn && player.isAlive()) {

                    for (Monster m : enemies) {
                        enemyGo(m, player);
                        if (!player.isAlive()) break;
                    }

                    enemyTurn = false;
                }
            }

            playerTurn = true;
        }

        checkPlayerDeath(player);
        return true;
    }

    private static void setUpFight() {
        playerTurn = true;
        enemyTurn = false;
        combatDone = false;
    }

    private static void playerGo(List<Monster> enemies, Player player) {
        wordWrapPrint("\nPlease input a move:");
        wordWrapPrint(Arrays.toString(moves));

        input = scanner.nextLine();
        input = input.toLowerCase().trim();
        ParsedCommand cmd = parseCommand(input);
        String leadInput = cmd.command;
        String arg = cmd.object;

        Monster enemy = null;

        if (leadInput.equals("attack") || leadInput.equals("a")) {
            //
            boolean validArg = false;

            if (arg == null) {
                enemy = enemies.get(rand.nextInt(enemies.size()));
                validArg = true;
            } else if (Integer.parseInt(arg) < enemies.size()+1) {
                enemy = enemies.get(Integer.parseInt(arg)-1);
                validArg = true;
            }

            if (validArg) {
                int playerDamage = player.getDamage();
                boolean playerCrit = player.getCrit();

                if (player.getPaperDoll().getWeapon() != null) {
                    print("You attack the " + enemy + " with the " +
                            player.getPaperDoll().getWeapon() + ".");
                } else {
                    print("You attack the " + enemy + " with your bare hands.");
                }

                if (playerCrit) {
                    print("Critical Hit!");
                    playerDamage *= 2;
                }

                print("You did "+playerDamage+" damage.\n");

                enemy.loseHP(playerDamage);

                playerTurn = false;

                if(enemyCleanUp(enemy, player)) {
                    enemies.remove(enemy);
                }
            } else {
                print("What are you trying to attack? (Enter the # to attack a specific enemy)");
            }
        } else if (leadInput.equals("magic") || leadInput.equals("m")) {
            List<Spells.Spell> spells = player.getSpellBook().getSpells();
            if (spells.size() == 0) {
                print("You have no spells.");
            } else {
                print(spells);
            }

            playerTurn = true;
        } else if (leadInput.equals("cast") || leadInput.equals("c")) {
            List<Spell> spells = player.getSpellBook().getSpells();
            if (arg == null) {
                print("What do you want to cast?");
            }
            else {
                if (player.getSpellBook().contains(arg)) {
                    Spell spell = player.getSpellBook().getSpell(arg);
                    print("You cast " + spell.toString() + "!");

                    for (Iterator<Monster> itr = enemies.iterator(); itr.hasNext();) {
                        Monster m = itr.next();
                        int spellDamage = spell.getDamage();
                        print(spell.toString() + " hits " + m + " for " + spellDamage + " damage.");
                        m.loseHP(spellDamage);

                        if(enemyCleanUp(m, player)) {
                            itr.remove();
                        }
                    }

                    playerTurn = false;
                } else {
                    print("You do not have " + arg + " in your spellbook.");
                }
            }
        } else if (leadInput.equals("flee") || leadInput.equals("f")) {
            if (player.skillCheck(DEX, 0, 1)) {
                print("You successfully ran away!");
                combatDone = true;
            } else {
                print("You failed to run away.");
                playerTurn = false;
            }
        } else if (leadInput.equals("inventory") || leadInput.equals("i")) {
            print(player.getInventory());
        } else if (leadInput.equals("use") || leadInput.equals("u")) {
            if (arg == null) {
                print("What do you want to use?");
            }
            else {
                if (GameInputHandler.performAction(player, USE, arg,
                        player.getInventory())) {
                    playerTurn = false;
                }
            }
        } else if (leadInput.equals("consume") || leadInput.equals("c")) {
            if (arg == null) {
                print("What do you want to consume?");
            }
            else {
                if (GameInputHandler.performAction(player, CONSUME, arg,
                        player.getInventory())) {
                    playerTurn = false;
                }
            }
        } else if (leadInput.equals("kill") || leadInput.equals("k") || leadInput.equals("ka")) {
            print("**Kill cheat invoked.  Cheater.**");
            //boolean validArg = false;
            if (arg == null || arg.equals("all") || leadInput.equals("ka")) {
                //enemy = enemies.get(rand.nextInt(enemies.size()));

                while (enemies.size() > 0) {
                    enemy = enemies.get(0);
                    enemy.setHP(0);
                    if(enemyCleanUp(enemy, player)) {
                        enemies.remove(enemy);
                    }
                }

            } else if (Integer.parseInt(arg) < enemies.size()+1) {
                enemy = enemies.get(Integer.parseInt(arg)-1);
                enemy.setHP(0);
                if(enemyCleanUp(enemy, player)) {
                    enemies.remove(enemy);
                }
            }

            playerTurn = false;
        } else {
            print("Invalid Move!");
        }

        for (Iterator<Monster> itr = enemies.iterator(); itr.hasNext();) {
            Monster m = itr.next();
            if(enemyCleanUp(m, player)) {
                enemies.remove(m);
            }
        }
    }

    private static void enemyGo(Monster enemy, Player player) {
        boolean enemyCrit = false;
        int enemyDamage;

        enemyDamage = enemy.getDamage();

        printnb("The " + enemy + " attacks.");
        print("");

        if (player.skillCheck(DEX, 0, 3)) {
            print("You successfully dodged!");

        }
        else {
            if (enemyCrit) {
                enemyDamage *= 2;
                printnb("Critical Hit!");
                print("");
            }

            if ((!player.getStatusList().contains(enemy.getStatusEffect())) &&
                    enemy.getStatusEffect() != null) {
                player.addStatus(enemy.getStatusEffect());
            }

            player.loseHP(enemyDamage);
        }

        print("");
    }

    private static boolean enemyCleanUp(Monster enemy, Player player) {
        if(enemy.hp() == 0) {
            print("\nYou have defeated the " + enemy + ".");

            player.getRoom().removeActor(enemy);

            Statistics.globalUpdate("monsterKillCount");
            if (enemy instanceof Rat) {
                Statistics.globalUpdate("ratKillCount");
            }
            player.narrator().postFightCommentary(player, enemy);

            if (!enemy.getInventory().isBarren()) {
                print("You looted:");
                enemy.getInventory().printInventory();

                /* ArrayList<Interacter> interactions = enemy.getInventory().interactions;
                for (Iterator<Interacter> itr = interactions.iterator(); itr.hasNext();) {
                    Interacter i = itr.next();
                    player.getInventory().add((Portable)i);
                    itr.remove();
                } */
            }
            print("");
            return true;
        } else return false;
    }

    private static void checkPlayerDeath(Player player) {
        if (player.hp() == 0) {
            print("\nYou have been defeated...");
        }
    }

    private static void oldCombatEngine(Monster enemy, Player player) {
        print("Starting fight!!...");
        print(enemy.battlecry());
        String playerMove;
        String enemyMove;
        String[] moves = {"attack", "block", "kill", "kill all"}; //kill cheat for easier testing
        int playerDamage;
        boolean playerCrit = false;
        boolean enemyCrit = false;
        Random rand = new Random();
        Scanner scanner = new Scanner(System.in);

        enemy.buff();

        printnb("Your HP: "+player.hp());
        printnb(" Enemy HP: "+enemy.hp());
        while(enemy.hp() > 0 && player.hp() > 0) {
            print("\nPlease input a move or press enter to do nothing "
                    + Arrays.toString(moves) + ": ");
            playerMove = scanner.nextLine();
            enemyMove = moves[rand.nextInt(2)];

            if(!playerMove.equals("") && !Arrays.asList(moves).contains(playerMove)) {
                print("Invalid Move!");
                continue;
            }

            if (playerMove.equals("kill")) { // Kill cheat
                print("**Kill cheat invoked.  Cheater.**");
                enemy.setHP(0);
            } else { // Standard combat steps
                // Player defend
                if (playerMove.equals(moves[1])) {
                    player.addDefense(1);
                    print("\nYou are defending.");
                }
                // Enemy defend
                if(enemyMove.equals(moves[1])) {
                    enemy.addDefense(1);
                    print("\nThe enemy is defending.");
                }
                // Player attack
                if (playerMove.equals(moves[0])) {
                    // Crit chance
                    if (rand.nextInt(2) == 1){
                        playerCrit = true;
                    }
                    // Calculates player dmg given enemy defense
                    playerDamage = player.attack(enemy.getDefense(),playerCrit);
                    if (playerCrit && playerDamage > 0) {
                        print ("\nCritical Hit!");
                    }
                    print("You did "+playerDamage+" damage.");
                    enemy.loseHP(playerDamage);
                    playerCrit = false;
                }
                // Enemy attack
                if (enemyMove.equals(moves[0]) && enemy.hp() > 0) {
                    int damage = enemy.attack(player.getDefense(),enemyCrit);
                    print("\nThe enemy attacked.");
                    if (damage > 0 && (!player.getStatusList().contains(enemy.getStatusEffect())) &&
                            enemy.getStatusEffect() != null) {
                        player.addStatus(enemy.getStatusEffect());
                        print("You have been afflicted with " + enemy.getStatusEffect() + ".");
                    }
                    player.loseHP(damage);
                }

                if(enemy.hp() < 0) {
                    enemy.setHP(0);
                }
                if(player.hp() < 0) {
                    player.setHP(0);
                }
            }
            printnb("Your HP: "+player.hp());
            printnb(" Enemy HP: "+enemy.hp());
            enemy.resetDefense();
            player.resetDefense();
        }
        if(enemy.hp() == 0) {
            print("\nYou have defeated the enemy.");
            Statistics.globalUpdate("monsterKillCount");
            if (enemy instanceof Rat) {
                Statistics.globalUpdate("ratKillCount");
            }
            player.narrator().postFightCommentary(player, enemy);

            /* if (!enemy.getInventory().isBarren()) {
                print("Here is your loot:");
                enemy.getInventory().printInventory();
                ArrayList<Interacter> interactions = enemy.getInventory().interactions;
                for (Iterator<Interacter> itr = interactions.iterator(); itr.hasNext();) {
                    //enemy.getInventory().remove(i.name());
                    Interacter i = itr.next();
                    player.getInventory().add((Portable)i);
                    itr.remove();
                }
            } */

        }
        else if(player.hp() == 0) {
            print("\nYou have been defeated.");
            player.death();
        }
    }

    public static void run(Questioner questioner, Player player, Module.Question qt) {
        questioner.question(player);
    }

}
