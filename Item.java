import java.io.Serializable;

public class Item implements Serializable {
    private String description;
    private String hiddenDescription;
    private String name;
    private String location;
    private int id;
    private boolean isVisible;

    public Item(String name, String description, String hiddenDescription) {
        this.name = name;
        this.description = description;
        this.hiddenDescription = hiddenDescription;
        this.isVisible = true;
    }

    public String getHiddenDescription() {return hiddenDescription; }

    public void setHiddenDescription(String hiddenDescription) {this.hiddenDescription = hiddenDescription; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
