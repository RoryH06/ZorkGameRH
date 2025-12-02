package Conditions;

import Game.Character;

public class EasyPickinsCondition implements RoomCondition {

    @Override
    public boolean canEnter(Character player) {
        return player.isWearingBOSSChain();
    }

    @Override
    public String getFailureMessage() {
        return "Youre too scared to enter, you know you will get beat up. You need something to look hard.";
    }
}
