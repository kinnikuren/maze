package util;
import static maze.Priority.*;
import maze.Priority;
import static util.Print.*;

public final class Logger {
    /* private static Loggers instance = null;

    public static Loggers getInstance() {
        if (instance == null) {
            instance = new Loggers(LOW);
        }
      return instance;
    } */
    private Priority logLevel;

    public Logger(Priority logLevel) {
        this.logLevel = logLevel;
    }

    public void log(String msg, Priority msgLevel) {
        if (msgLevel.compareTo(logLevel) >= 0) {
            print("Log>> " + msg);
        }
    }

    public void log(String msg) {
        if (DEFAULT.compareTo(logLevel) >= 0) {
            print("Log>> " + msg);
        }
    }

    public void higher() {
        logLevel = upShift(logLevel);
    }

    public void lower() {
        logLevel = downShift(logLevel);
    }

    public Priority getLevel() { return logLevel; }

    public void setLevel(Priority logLevel) {
        this.logLevel = logLevel;
    }
}
