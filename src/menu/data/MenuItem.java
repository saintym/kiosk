package menu.data;

public class MenuItem {
    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private int price;
    public int getPrice() {
        return price;
    }
    private String description;
    public String getDescription() {
        return description;
    }

    public MenuItem(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
