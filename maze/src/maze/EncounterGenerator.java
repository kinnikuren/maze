package maze;

import static util.Print.print;
import static util.Loggers.*;

import java.lang.reflect.Method;
import java.util.Random;

public class EncounterGenerator {
    private static Random rand = new Random();

    public static void run(Player player){

        /* Run encounters */

        Parade Parade = new Parade();

        Method[] m = Parade.class.getDeclaredMethods();
        Method encounter;

        int num = rand.nextInt(m.length);
        int x = 0;
        String methodName = null;
        Class params[] = new Class[2];
        params[0] = Player.class;
        params[1] = String.class;

        //System.out.println(m.length);

        //player.tracker().getTracker().containsValue(value)

        while(!player.tracker().getTracker().containsValue(x)) {
            x++;
        }

        do {
            methodName = (String) player.tracker().getTracker().keySet().toArray()[num];
            num = rand.nextInt(m.length);
        } while(player.tracker().getTracker().get(methodName) != x);

        //System.out.println(methodName);

        try {
            //m[num].invoke(Parade,player,m[num].getName());
            encounter = Parade.class.getDeclaredMethod(methodName, params);
            encounter.invoke(Parade, player, methodName);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

        /*
        for (Method m:Parade.class.getDeclaredMethods()) {
            //System.out.println(m.getName());
            try {
                m.invoke(Parade,player,m.getName());
            } catch (Exception e) {
                 System.out.println("Exception: " + e);
            }
        }
        for (Method m:Parade.class.getDeclaredMethods()) {
            //System.out.println(m.getName());
            try {
                m.invoke(Parade,player,m.getName());
            } catch (Exception e) {
                 System.out.println("Exception: " + e);
            }
        }
        */
        for (String key : player.tracker().getTracker().keySet()) {
            log(">" + key + ": " + player.tracker().getTracker().get(key));
        }
    }

}
