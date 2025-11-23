package Game;

public interface RoomCondition {
    boolean canEnter(Character player);
    String getFailureMessage();
}
