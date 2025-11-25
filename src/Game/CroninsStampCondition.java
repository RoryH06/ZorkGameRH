package Game;

public class CroninsStampCondition implements RoomCondition {

    @Override
    public boolean canEnter(Character player) {
        return player.hasStamp();   // you define this in Player
    }

    @Override
    public String getFailureMessage() {
        return "You cannot enter. You dont have a stamp.";
    }
}