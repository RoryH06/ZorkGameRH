package Conditions;

import Game.Character;

public class RorysKeysCondition implements RoomCondition {

    @Override
    public boolean canEnter(Character player) {
        return player.isHoldingKeys();   // you define this in Player
    }

    @Override
    public String getFailureMessage() {
        return "You cannot enter, the doors locked.";
    }
}
