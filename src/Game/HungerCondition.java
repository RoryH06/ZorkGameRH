package Game;

public class HungerCondition implements RoomCondition {

    @Override
    public boolean canEnter(Character player) {
        return player.isHungry();   // you define this in Player
    }

    @Override
    public String getFailureMessage() {
        return "You cannot enter. You are not hungry.";
    }
}
