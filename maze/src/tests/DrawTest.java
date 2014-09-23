package tests;

import static util.Loggers.log;
import static util.Loggers.programLog;
import static util.Print.*;
import game.core.events.Priority;
import game.core.maze.Maze;
import game.core.maze.MazeFactory;
import game.core.maze.MazeMap.Gate;
import game.core.positional.Cardinals;
import game.core.positional.Coordinate;
import game.core.positional.Coordinates;
import game.objects.units.Player;
import game.player.util.Statistics;

import java.awt.Color;
import java.awt.Font;
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

import util.Paired;
import util.View;

public class DrawTest extends JPanel {
    public static Maze coolMaze;

       public static void main(String[] args) {
           programLog.setLevel(Priority.HIGH);

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

           util.Print.print(coolMaze.map().viewTwoLinkNodes());


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

           int shapeSize = 40;
           int sizeFactor = 65;

           HashSet<Coordinate> mazeMap;
           View<Coordinate> viewMap = coolMaze.map().viewAllNodes();

          Graphics2D g2 = (Graphics2D) g;

          g.setFont(new Font("Arial", Font.PLAIN, 10));

          //g2.draw(new Line2D.Double(0, 0, 10, 10));
          //g2.draw(new Rectangle2D.Double(0, 0, 40, 40));

          for (Coordinate c : viewMap) {
              int x = x_center + c.x()*sizeFactor;
              int y = y_center - c.y()*sizeFactor;

              //center of the shape
              int x_shape = x + shapeSize/2;
              int y_shape = y + shapeSize/2;

              String coord = "(" + String.valueOf(c.x()) + ", " + String.valueOf(c.y()) + ")";

              //g2.draw(new Rectangle2D.Double(x, y, 40, 40));
              g2.draw(new Ellipse2D.Double(x, y, shapeSize, shapeSize));
              g.drawString(coord, x+2, y);

              int y_item = y+10;

              if (coolMaze.getRoom(c).contains("The Darkness")) {
                  g.drawString("DARKNESS", x+5, y_item+=10);
              }
              if (coolMaze.getRoom(c).contains("Oily Rag")) {
                  g.drawString("Rag", x+5, y_item+=10);
              }
              if (coolMaze.getRoom(c).contains("Wooden Stick")) {
                  g.drawString("Stick", x+5, y_item+=10);
              }
              if (coolMaze.getRoom(c).contains("Matches")) {
                  g.drawString("Matches", x+5, y_item+=10);
              }
              if (coolMaze.getRoom(c).contains("Locked Gate")) {
                  g.drawString("Gate", x+5, y_item+=10);
              }
              if (coolMaze.getRoom(c).contains("Plain Key")) {
                  g.drawString("Key", x+5, y_item+=10);
              }
              if (coolMaze.getRoom(c).contains("Red Door")) {
                  g.drawString("Red Door", x+5, y_item+=10);
              }
              if (coolMaze.getRoom(c).contains("Red Key")) {
                  g.drawString("Red Key", x+5, y_item+=10);
              }
              if (coolMaze.getRoom(c).contains("Blue Door")) {
                  g.drawString("Blue Door", x+5, y_item+=10);
              }
              if (coolMaze.getRoom(c).contains("Blue Key")) {
                  g.drawString("Blue Key", x+5, y_item+=10);
              }
              if (coolMaze.getRoom(c).contains("Purple Door")) {
                  g.drawString("Purple Door", x+5, y_item+=10);
              }
              if (coolMaze.getRoom(c).contains("Rope Bridge")) {
                  g.drawString("Rope Bridge", x+5, y_item+=10);
              }

              View<Coordinate> viewNeighbors = coolMaze.viewNeighborsOf(c);
              for (Coordinate neighbor : viewNeighbors) {
                  int n_x = x_center + neighbor.x()*sizeFactor;
                  int n_y = y_center - neighbor.y()*sizeFactor;

                  // center of neighbor shape
                  int n_x_shape = n_x + shapeSize / 2;
                  int n_y_shape = n_y + shapeSize / 2;

                  int x_diff = n_x - x;
                  int y_diff = y - n_y;

                  // midpoint between c and neighbor
                  double x_arrow;
                  if (x != 0) x_arrow = x_shape + (x_diff / 2);
                  else x_arrow = x;

                  double y_arrow;
                  if (y != 0 ) y_arrow = y_shape - (y_diff / 2);
                  else y_arrow = y;

                  /* positive x = east, neg. x = west
                   * pos. y = north, neg. y = south
                   */
                  Coordinate diff = Coordinates.diff(c, neighbor);
                  Cardinals dir = Cardinals.get(diff);

                  if (!coolMaze.viewNeighborsOf(neighbor).contains(c)) {
                      g2.setColor(Color.RED);
                      g2.draw(new Line2D.Double(x+20, y+20, n_x+20, n_y+20));
                  } else {
                      g2.setColor(Color.BLUE);
                      g2.draw(new Line2D.Double(x+20, y+20, n_x+20, n_y+20));
                  }

               // line slope
                  double angle;
                  if (x_diff != 0) {
                      if (y_diff < 0 && x_diff < 0) {
                          angle = Math.atan(y_diff/x_diff) + Math.PI;
                      } else if (y_diff >= 0 && x_diff < 0) {
                          angle = Math.atan(y_diff/x_diff) + Math.PI;
                      } else if (y_diff < 0 && x_diff >= 0) {
                          angle = Math.atan(y_diff/x_diff) + Math.PI*2;
                      } else {
                          angle = Math.atan(y_diff/x_diff);
                      }
                  } else if (y_diff < 0) {
                      angle = Math.PI * 3/2;
                  } else {
                      angle = Math.PI / 2;
                  }
                  System.out.println("Coordinate: " + c + " Neighbor: " + neighbor);
                  System.out.println("angle: " + angle*180/Math.PI);

                  double x_arrowbase;
                  double y_arrowbase;

                  x_arrowbase = x_arrow - 5*Math.cos(angle);
                  y_arrowbase = y_arrow + 5*Math.sin(angle);

                  double x_arrow1 = x_arrowbase - 5*Math.cos(Math.PI/2 - angle);
                  double y_arrow1 = y_arrowbase - 5*Math.sin(Math.PI/2 - angle);

                  double x_arrow2 = x_arrowbase + 5*Math.sin(angle);
                  double y_arrow2 = y_arrowbase + 5*Math.cos(angle);

                  g2.draw(new Line2D.Double(x_arrow, y_arrow, x_arrow1, y_arrow1));
                  g2.draw(new Line2D.Double(x_arrow, y_arrow, x_arrow2, y_arrow2));

                  /*
                  System.out.println("Coordinate: " + x + ", " + y);
                  System.out.println("Neighbor: " + n_x + ", " + n_y);
                  System.out.println("Shape Center: " + x_shape + ", " + y_shape);
                  System.out.println("N Shape Center: " + n_x_shape + ", " + n_y_shape);
                  //System.out.println
                  //System.out.println

                  System.out.println(x_diff);
                  System.out.println(y_diff);
                  System.out.println(x_arrow);
                  System.out.println(y_arrow);
                  //g2.draw(new Ellipse2D.Double(x_arrow, y_arrow, 5, 5));
                   *
                   */

              }

              g2.setColor(Color.BLACK);
              if (coolMaze.exit().equals(c)) {
                  g.drawString("EXIT", x+5, y+10);
              }
          }

          for (Gate gate : coolMaze.map().getGates()) {
              g2.setColor(Color.GREEN);
              Paired<Coordinate> gc = gate.getCoords();
              int g_x1 = x_center + gc.o1.x()*sizeFactor + shapeSize/2;
              int g_y1 = y_center - gc.o1.y()*sizeFactor + shapeSize/2;
              int g_x2 = x_center + gc.o2.x()*sizeFactor + shapeSize/2;
              int g_y2 = y_center - gc.o2.y()*sizeFactor + shapeSize/2;
              g2.draw(new Line2D.Double(g_x1, g_y1, g_x2, g_y2));

          }
       }
    }