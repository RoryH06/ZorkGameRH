package Conditions;

import Game.Character;

public interface RoomCondition {
    boolean canEnter(Character player);
    String getFailureMessage();
}
