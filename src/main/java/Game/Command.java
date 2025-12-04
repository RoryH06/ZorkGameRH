package Game;

public class Command {
    private String commandWord; //insatance varaibles for verb and second word
    private String secondWord;

    public Command(String firstWord, String secondWord) {
        this.commandWord = firstWord; //constructor
        this.secondWord = secondWord;
    }

    public String getCommandWord() {
        return commandWord;
    }

    public String getSecondWord() {
        return secondWord;
    }

    public boolean hasSecondWord() {
        return secondWord != null;
    }
}
