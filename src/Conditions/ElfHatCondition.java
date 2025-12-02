package Conditions;

import Game.Character;

public class ElfHatCondition implements RoomCondition {

    @Override
    public boolean canEnter(Character player) {
        return player.isWearingElfHat();
    }

    @Override
    public String getFailureMessage() {
        return "You cannot enter. You're not an elf";
    }
}
