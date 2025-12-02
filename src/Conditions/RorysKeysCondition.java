package Conditions;

import Game.Character;

import java.io.Serializable;

public class RorysKeysCondition implements RoomCondition {

    @Override
    public boolean canEnter(Character player) {
        return player.isHoldingKeys();  
    }

    @Override
    public String getFailureMessage() {
        return "You cannot enter, the doors locked.";
    }
}
