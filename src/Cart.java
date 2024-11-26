import menu.data.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Cart {
    private List<MenuItem> items = new ArrayList<>();

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public void removeItem(String itemName) {
        items.removeIf(item -> item.getName().equalsIgnoreCase(itemName));
    }

    public Map<String, Integer> getItems() {
        return items.stream()
                .collect(Collectors.groupingBy(
                        MenuItem::getName,
                        Collectors.summingInt(item -> 1)
                ));
    }

    public boolean removeItemByIndex(int index) {
        var itemNames = new ArrayList<>(getItems().keySet());
        if (index <= 0 || index > itemNames.size()) {
            return false;
        }
        String itemNameToRemove = itemNames.get(index - 1);
        return items.removeIf(item -> item.getName().equals(itemNameToRemove));
    }

    public int calculateDiscountedPrice(int type) {
        DISCOUNT discountType;

        try {
            if (type < 0)
                return -1;
            discountType = DISCOUNT.values()[type];
        } catch (ArrayIndexOutOfBoundsException e) {
            return -1;
        }

        double discountRate = discountType.getDiscountRate();

        double total = calculateTotal();
        double discountedPrice = total * (1 - discountRate);

        return (int) discountedPrice;
    }


    public int calculateTotal() {
        return items.stream()
                .mapToInt(MenuItem::getPrice)
                .sum();
    }

    public void clearCart() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}