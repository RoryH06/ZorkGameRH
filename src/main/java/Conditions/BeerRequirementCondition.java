package Conditions;

import Game.Character; //importing character class

public class BeerRequirementCondition implements RoomCondition {

    public final int requiredDrinks; //declare variables

    public BeerRequirementCondition(int requiredDrinks) {
        this.requiredDrinks = requiredDrinks; //constructor
    }

    @Override //override the roomcondition interface
    public boolean canEnter(Character player) {
        return player.getDrinkCount() >= requiredDrinks;
    }  //makes it so the player can only enter if they have enough drinks

    @Override
    public String getFailureMessage() {
        return "You need to drink at least " +  requiredDrinks + " drinks or else you cannot enter.";
    }
} //displays a failure message
