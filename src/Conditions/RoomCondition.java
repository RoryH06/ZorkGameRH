package Conditions;

import Game.Character;

import java.io.Serializable;

public interface RoomCondition extends Serializable {
    boolean canEnter(Character player);
    String getFailureMessage();
}
