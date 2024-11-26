import menu.MenuManager;
import menu.data.Menu;
import menu.data.MenuItem;

class Main {
    public static void main(String[] args) {
        var kiosk = new Kiosk();
        kiosk.start();
    }
}

public class Kiosk {
    private final InteractionManager interactionManager = new InteractionManager();
    private final MenuManager menuManager = new MenuManager();
    private final Cart cart = new Cart();

    public void start() {
        while (true) {
            int selectedMenuIndex = promptMainMenu();
            if(isExit(selectedMenuIndex))
                return;

            if(!cart.isEmpty() &&
                    (isOrder(selectedMenuIndex) || isCancelOrder(selectedMenuIndex)))
            {
                if (isOrder(selectedMenuIndex)) {
                    interactionManager.showMenuItemInCart(cart);
                    var userSelected = interactionManager.read();
                    if(userSelected == 1){
                        interactionManager.showOrderDoneMessage(cart.getAllPrice());
                        interactionManager.off();
                        return;
                    }
                    continue;
                } else if (isCancelOrder(selectedMenuIndex)) {
                    interactionManager.showMessage("\n장바구니를 비웁니다.\n");
                    cart.clearCart();
                    continue;
                }
            }

            if (cart.isEmpty()){
                var a = menuManager.getAllMenu().size();
                if(selectedMenuIndex > a) {
                    interactionManager.showError();
                    continue;
                }
            }

            var selectedMenu = menuManager.getMenuByIndex(selectedMenuIndex);
            var selectedMenuItemIndex = promptProductMenu(selectedMenu);

            if (isExit(selectedMenuItemIndex))
                continue;

            var items = selectedMenu.getMenuItems();
            var menuItem = items.get(selectedMenuItemIndex-1);
            promptMenuInCart(menuItem);
        }
    }


    private void promptMenuInCart(MenuItem item) {
        interactionManager.showTargetMenuItem(item);
        interactionManager.showPutInCart(item);
        boolean trigger = true;
        while(trigger){
            var userSelected = interactionManager.read();
            if(!(userSelected == 1 || userSelected == 2)) {
                interactionManager.showError();
                continue;
            }
            if (userSelected == 1)
                cart.addItem(item);
            trigger = false;
        }
    }

    private int promptProductMenu(Menu menu) {
        var targetMenu = menuManager.getMenu(menu);
        interactionManager.showTargetMenu(targetMenu);
        var userSelected = interactionManager.read();

        if (userSelected > targetMenu.getMenuItems().size()
                || userSelected < 0) {
            interactionManager.showError();
            return promptProductMenu(menu);
        }
        if (userSelected == 0)
            return -1;

        return userSelected;
    }

    private int promptMainMenu() {
        var allMenu = menuManager.getAllMenu();

        interactionManager.showMainMenu(allMenu);
        if (!cart.isEmpty()) {
            interactionManager.showOrderMenu(allMenu.size());
        }

        var userSelected = interactionManager.read();

        int maxOption = cart.isEmpty() ? allMenu.size() : allMenu.size() + 2;
        if (userSelected < 0 || userSelected > maxOption) {
            interactionManager.showError();
            return promptMainMenu();
        }

        if (userSelected == 0) {
            interactionManager.off();
            return -1;
        }

        return userSelected;
    }

    private boolean isOrder(int selected) {
        return selected == menuManager.getAllMenu().size()+1;
    }

    private boolean isCancelOrder(int selected) {
        return selected == menuManager.getAllMenu().size()+2;
    }

    private boolean isExit(int index) {
        return index == -1;
    }


}