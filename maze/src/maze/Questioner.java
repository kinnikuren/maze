package maze;

public interface Questioner extends Interacter {
    public Boolean question(Player player);
    //should return null when questioning is no longer valid
    //check for null returns appropriately
}
