package menu.data;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private String name = "";

    public String getName() {
        return this.name;
    }
    private List<MenuItem> menuItems = new ArrayList<>();
    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public Menu(String name) {
        this.name = name;
    }

    public void addMenuItem(String name, int price, String description) {
        menuItems.add(new MenuItem(name, price, description));
    }

    public boolean removeMenuItem(String name) {
        return menuItems.removeIf(item -> item.getName().equals(name));
    }
}
