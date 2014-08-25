package maze;

import java.util.PriorityQueue;

public final class Stages {
    private Stages() { } //no instantiation

    private static abstract class AbstractStage implements Stage { }

    public static Stage combine(final Stage...stages) {
        Stage combinedStage = new AbstractStage() {

            @Override
            public String getName() {
                return "";
            }
          @Override
          public void removeActor(Interacter actor) { }
          @Override
          public void cleanupActors() { }
          @Override
          public PriorityQueue<Event> getCurrentEvents() {
              PriorityQueue<Event> combinedQueue = new PriorityQueue<Event>();
              for (Stage stage : stages) {
                combinedQueue.addAll(stage.getCurrentEvents());
              }
            return combinedQueue;
          }
          @Override
          public PriorityQueue<Event> getCurrentEvents(Commands trigger) {
              PriorityQueue<Event> combinedQueue = new PriorityQueue<Event>();
              for (Stage stage : stages) {
                combinedQueue.addAll(stage.getCurrentEvents(trigger));
              }
            return combinedQueue;
          }
          @Override
          public PriorityQueue<Event> getCurrentEvents(Commands trigger, String objectName) {
              PriorityQueue<Event> combinedQueue = new PriorityQueue<Event>();
              for (Stage stage : stages) {
                combinedQueue.addAll(stage.getCurrentEvents(trigger, objectName));
              }
            return combinedQueue;
          }
          @Override
          public boolean contains(String objectName) {
              for (Stage stage : stages) {
                if (stage.contains(objectName))
                return true;
              }
            return false;
          }
          @Override
          public boolean isBarren() {
              for (Stage stage : stages) {
                if (!stage.isBarren())
                return true;
              }
            return false;
          }
        };
      return combinedStage;
    }
}
