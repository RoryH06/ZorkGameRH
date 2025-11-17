public class DrinkItem extends Item implements Drinkable {

    public DrinkItem(String name, String description, String hiddenDescription) {
        super(name, description, hiddenDescription);
    }

    @Override
    public String drink() {
        return "You drink the " + getName() + ". " + getDescription();
    }
}
