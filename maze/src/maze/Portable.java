package maze;

public interface Portable extends Interacter {
    //public Event interact(Commands trigger, Stage stage); //main interaction method for Portables, supercedes interact method of Interacter

    public void pickedUp(); //set status to picked up

    public void dropped(); //set status to dropped

    public String details();

    public int weight();

}
