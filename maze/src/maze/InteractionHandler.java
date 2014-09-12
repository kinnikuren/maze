package maze;

import java.util.*;

import maze.Bestiary.Monster;
import maze.Bestiary.Rat;
import static util.Print.*;

public final class InteractionHandler {
    private InteractionHandler() { } //no instantiation

    public static void run(Monster enemy, Player player, Module.Fight ft) {
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
                    if (damage > 0 && (!player.status.contains(enemy.getStatusEffect())) &&
                            enemy.getStatusEffect() != null) {
                        player.setStatus(enemy.getStatusEffect());
                        print("You have been afflicted with " + player.status + ".");
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
            //enemy.death();
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
