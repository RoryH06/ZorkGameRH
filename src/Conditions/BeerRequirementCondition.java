package Conditions;

import Game.Character;

public class BeerRequirementCondition implements RoomCondition {

    public final int requiredDrinks;

    public BeerRequirementCondition(int requiredDrinks) {
        this.requiredDrinks = requiredDrinks;
    }

    @Override
    public boolean canEnter(Character player) {
        return player.getDrinkCount() >= requiredDrinks;
    }

    @Override
    public String getFailureMessage() {
        return "You need to drink at least " +  requiredDrinks + " drinks or else you cannot enter.";
    }
}
