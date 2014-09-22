package tests;

import static util.Loggers.log;
import static util.Loggers.programLog;
import static util.Print.*;
import game.core.events.Priority;
import game.core.maze.Maze;
import game.core.maze.MazeFactory;
import game.core.positional.Coordinate;
import game.objects.units.Player;
import game.player.util.Statistics;

import java.awt.Graphics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

import util.View;

public class DrawTest extends JPanel {
    public static Maze coolMaze;

       public static void main(String[] args) {
           programLog.setLevel(Priority.DORMANT);

           Scanner scanner = new Scanner(System.in);
           String input;
           String name;

           util.Print.print("Constructing cool Maze...");

           HashSet<Coordinate> openSet = new HashSet<Coordinate>();
           Coordinate center = new Coordinate();
           openSet.add(center);
           openSet.add(new Coordinate(0,1));
           coolMaze = MazeFactory.buildMaze(openSet, 30, center);

           Statistics.initialize();

           //Maze coolMaze = MazeFactory.buildMaze(30);

           //coolMaze.setExit(new Coordinate(1,1));
           log("Exit location => " + coolMaze.exit());
           log("Center location => " + coolMaze.center());
           log("Final size: " + coolMaze.size());

           coolMaze.populateRooms();

           //AStar.discover(coolMaze);
           util.Print.print("\nMaze Run Test 4.0 :: Maze project.");
           util.Print.print("Ready for user input.");

           util.Print.print("------------------");
           util.Print.print("------------------");
           util.Print.print("What is your name? ");
            //name = scanner.nextLine();
           name = "Test Guy"; // for testing
           Player you = new Player(coolMaze);
           you.setName(name);

           util.Print.print("\nWelcome to the Maze, " + you.name() + "!");


          JFrame f = new JFrame();
          f.setSize(1200, 600);
          f.add(new DrawTest());
          f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          f.setVisible(true);
       }

       public void paint(Graphics g) {
          //g.fillRect (5, 15, 50, 75);
           int x_center = 600;
           int y_center = 300;

           HashSet<Coordinate> mazeMap;
           View<Coordinate> viewMap = coolMaze.map().viewAllNodes();

          Graphics2D g2 = (Graphics2D) g;

          //g2.draw(new Line2D.Double(0, 0, 10, 10));
          //g2.draw(new Rectangle2D.Double(0, 0, 40, 40));

          for (Coordinate c : viewMap) {
              int x = x_center + c.x()*55;
              int y = y_center + c.y()*55;

              String coord = "(" + String.valueOf(c.x()) + ", " + String.valueOf(c.y()) + ")";

              //g2.draw(new Rectangle2D.Double(x, y, 40, 40));
              g2.draw(new Ellipse2D.Double(x, y, 40, 40));
              g.drawString(coord, x+2, y);

              View<Coordinate> viewNeighbors = coolMaze.viewNeighborsOf(c);
              for (Coordinate neighbor : viewNeighbors) {
                  int n_x = x_center + neighbor.x()*55;
                  int n_y = y_center + neighbor.y()*55;
                  g2.draw(new Line2D.Double(x+20, y+20, n_x+20, n_y+20));
              }

              if (coolMaze.exit().equals(c)) {
                  g.drawString("EXIT", x+5, y+20);
              }
          }
       }
    }