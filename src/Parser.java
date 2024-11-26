import menu.data.Menu;
import menu.data.MenuItem;

import java.util.List;
import java.util.stream.Collectors;

public class Parser {
    public static String parseMenuToString(Menu menu) {
        var builder = new StringBuilder("[ " + menu.getName().toUpperCase() + " MENU ]\n");
        int index = 1;
        for (var item : menu.getMenuItems()) {
            builder.append(index).append(". ")
                    .append(parseMenuitemToString(item));
            index++;
        }
        return builder.toString();
    }

    public static String parseMenuitemToString(MenuItem menuItem) {
        String name = menuItem.getName();
        String price = menuItem.getPrice() + " W";
        String description = menuItem.getDescription();
        return name + "\t|\t" + price + "\t|\t" + description + "\n";
    }

    public static String parseMenuNamesToString(List<Menu> menu) {
        return menu.stream()
                .map(item -> (menu.indexOf(item) + 1) + ". " + item.getName().toUpperCase())
                .collect(Collectors.joining("\n")) + "\n";
    }
}
