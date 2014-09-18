//: net/mindview/util/Print.java
// Print methods that can be used without
// qualifiers, using Java SE5 static imports:
package util;
import static util.Print.print;

import java.io.*;

public class Print {
  // Print with a newline:
  public static void print(Object obj) {
    System.out.println(obj);
  }
  // Print a newline by itself:
  public static void print() {
    System.out.println();
  }
  // Print with no line break:
  public static void printnb(Object obj) {
    System.out.print(obj);
  }
  // The new Java SE5 printf() (from C):
  public static PrintStream
  printf(String format, Object... args) {
    return System.out.printf(format, args);
  }

  public static void wordWrapPrint(String text) {
      int i=0;
      int j;
      if (text.length() > 100) {
          do {
              j = i + 100;
              if (text.charAt(j) != ' ') {
                  j = text.indexOf(" ",j);
              }
              if (j == -1) {
                  break;
              }
              print(text.substring(i,j));
              i = j+1;
          } while(text.length()-i > 100);
          print(text.substring(i,text.length()));
      } else {
          print(text);
      }
  }
} ///:~
