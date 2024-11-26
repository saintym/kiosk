package menu;

import menu.data.Menu;
import menu.data.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuManager {
    private List<Menu> menu;

    public MenuManager() {
        this.menu = new ArrayList<>();
        registerDefaultMenu();
    }

    public Menu getMenu(Menu menu){
        return this.menu.stream()
                .filter(item -> item.getName().equals(menu.getName()))
                .findFirst()
                .get();
    }

    public Menu getMenuByIndex(int selected) {
        var index = selected-1;
        if(index > menu.size() || index < 0)
            throw new IllegalArgumentException("Cannot select that menu, maximum menu number is " + menu.size());
        return menu.get(index);
    }

    public MenuItem getMenuItemByIndex(String menuName, int index) {
        var targetMenu = menu.stream()
                .filter(menu -> menu.getName().equals(menuName))
                .findFirst().get();
        return targetMenu.getMenuItems().get(index);
    }

    public List<Menu> getAllMenu() {
        return menu;
    }

    private void registerDefaultMenu() {
        var burgers = new Menu("Burgers");
        burgers.addMenuItem("ShackBurger", 6900, "토마토, 양상추, 쉑소스가 토핑된 치즈버거");
        burgers.addMenuItem("SmokeShack", 8900, "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거");
        burgers.addMenuItem("Cheese burger", 6900, "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거");
        burgers.addMenuItem("Hamburger", 5400, "비프패티를 기반으로 야채가 들어간 기본버거");

        var drinks = new Menu("Drinks");
        drinks.addMenuItem("Ice Americano", 4500, "졸리면 드셔야합니다");
        drinks.addMenuItem("Cola", 2000, "반드시 드셔야합니다");
        drinks.addMenuItem("Sol's Eye", 10000, "드시면 아니되옵니다");

        var desserts = new Menu("Desserts");
        desserts.addMenuItem("Icecream", 3000, "차갑고 달달합니다");
        desserts.addMenuItem("ChickenNugget 5pc", 150, "사장님이 미쳤어요");

        menu.add(burgers);
        menu.add(drinks);
        menu.add(desserts);
    }
}
