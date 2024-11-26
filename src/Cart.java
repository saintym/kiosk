import menu.data.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<MenuItem> items = new ArrayList<>();

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public void removeItem(String itemName) {
        items.removeIf(item -> item.getName().equalsIgnoreCase(itemName));
    }

    public List<MenuItem> getItems() {
        return new ArrayList<>(items);
    }

    public int getAllPrice() {
        var price = 0;
        for(var item : items){
            price = price + item.getPrice();
        }
        return price;
    }

    public double calculateTotal() {
        return items.stream()
                .mapToDouble(MenuItem::getPrice)
                .sum();
    }

    public void clearCart() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}