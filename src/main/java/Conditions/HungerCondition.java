package Conditions;

import Game.Character;

public class HungerCondition implements RoomCondition {

    @Override
    public boolean canEnter(Character player) {
        return player.isHungry();
    }

    @Override
    public String getFailureMessage() {
        return "You cannot enter. You are not hungry.";
    }
}
