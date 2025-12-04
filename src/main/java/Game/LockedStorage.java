package Game;

public class LockedStorage extends Storage {
    private String code;
    private boolean locked = true;

    public LockedStorage(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean tryCode(String attempt) {
        if (attempt.equals(code)) {
            locked = false;
            return true;
        }
        return false;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public Item lootItem(String itemName, Character character) {
        if (locked) {
            System.out.println("The chest is locked. You can't loot anything yet.");
            return null;
        }
        return super.lootItem(itemName, character);
    }
}