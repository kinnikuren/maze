package maze;

//import static util.Print.print;
public class RoomMapMaker {

    public static void roomify(Maze maze) {
        for (Coordinate c : maze.viewPositions()) {
            if (maze.getRoom(c) == null) {
                //r.populateRoom();
                //maze.buildOldRoom(newRoom);
                maze.buildRoom(c);
            }
        }
    }
}
