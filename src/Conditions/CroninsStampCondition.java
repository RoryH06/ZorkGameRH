package Conditions;

import Game.Character;

public class CroninsStampCondition implements RoomCondition {

    @Override
    public boolean canEnter(Character player) {
        return player.hasStamp();
    }

    @Override
    public String getFailureMessage() {
        return "You cannot enter. You dont have a stamp.";
    }
}