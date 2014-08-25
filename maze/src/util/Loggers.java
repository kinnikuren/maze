package util;

import maze.Priority;

public final class Loggers {
    private Loggers() { } //no instantiation

    public static final Logger programLog = new Logger(Priority.DEFAULT);

    public static void log(String msg, Priority msgLevel) {
        programLog.log(msg, msgLevel);
    }

    public static void log(String msg) {
        programLog.log(msg);
    }
}
