package game.core.maze;

import static util.Loggers.log;
import static util.Loggers.programLog;
import game.core.events.Priority;
import game.core.positional.Coordinate;
import game.objects.units.Player;
import game.player.util.Statistics;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.HashSet;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

import tests.DrawTest;
import util.View;

public class MazeDisplay extends JPanel {
    protected static Maze mazeCopy;
    protected static Player playerCopy;
    protected static Coordinate playerLoc;

    public static void run(Maze maze, Player player) {
       mazeCopy = maze;
       playerCopy = player;
       playerLoc = player.location();

       JFrame f = new JFrame();
       f.setSize(1200, 600);
       f.add(new MazeDisplay());
       f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
       f.setVisible(true);
   }

   public void paint(Graphics g) {
      //g.fillRect (5, 15, 50, 75);
       int x_center = 600;
       int y_center = 300;

       HashSet<Coordinate> mazeMap;
       View<Coordinate> viewMap = mazeCopy.map().viewAllNodes();

       Graphics2D g2 = (Graphics2D) g;

       g.setFont(new Font("Arial", Font.PLAIN, 10));

       for (Coordinate c : viewMap) {
           if (playerCopy.getPath().contains(c)) {

               int x = x_center + c.x()*55;
               int y = y_center - c.y()*55;

               //String coord = "(" + String.valueOf(c.x()) + ", " + String.valueOf(c.y()) + ")";

               //g2.draw(new Rectangle2D.Double(x, y, 40, 40));
               g2.draw(new Ellipse2D.Double(x, y, 40, 40));
               //g.drawString(coord, x+2, y);

               int y_item = y+10;

               View<Coordinate> viewNeighbors = mazeCopy.viewNeighborsOf(c);
               for (Coordinate neighbor : viewNeighbors) {
                   if (playerCopy.getPath().contains(neighbor)) {
                       int n_x = x_center + neighbor.x()*55;
                       int n_y = y_center - neighbor.y()*55;
                       g2.draw(new Line2D.Double(x+20, y+20, n_x+20, n_y+20));
                   }
               }

               if (playerLoc.equals(c)) {
                   g.drawString("YOU", x+10, y+20);
               }

               /*
               if (mazeCopy.exit().equals(c)) {
                   g.drawString("EXIT", x+5, y+20);
               }*/
           }
      }
   }
}
