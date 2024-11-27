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
            if (isExit(selectedMenuIndex))
                return;

            if (isCartMenuSelected(selectedMenuIndex)) {
                if (isOrder(selectedMenuIndex)) {
                    handleOrderMenu();
                    continue;
                }
                if (isCancelOrder(selectedMenuIndex)) {
                    interactionManager.showMessage("\n장바구니를 비웁니다.\n");
                    cart.clearCart();
                    continue;
                }
            }

            if (cart.isEmpty()) {
                if (selectedMenuIndex > menuManager.getAllMenu().size()) {
                    interactionManager.showError();
                    continue;
                }
            }

            var selectedMenu = menuManager.getMenuByIndex(selectedMenuIndex);
            var selectedMenuItemIndex = promptProductMenu(selectedMenu);

            if (isExit(selectedMenuItemIndex))
                continue;

            var items = selectedMenu.getMenuItems();
            var menuItem = items.get(selectedMenuItemIndex - 1);
            promptMenuInCart(menuItem);
        }
    }

    private void promptMenuInCart(MenuItem item) {
        interactionManager.showTargetMenuItem(item);
        interactionManager.showPutInCart(item);
        boolean trigger = true;
        while (trigger) {
            var userSelected = interactionManager.read();
            if (!(userSelected == 1 || userSelected == 2)) {
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

        int maxOptionCount = cart.isEmpty() ? allMenu.size() : allMenu.size() + 2;
        if (userSelected < 0 || userSelected > maxOptionCount) {
            interactionManager.showError();
            return promptMainMenu();
        }

        if (userSelected == 0) {
            interactionManager.off();
            return -1;
        }

        return userSelected;
    }

    private void handleOrderMenu() {
        while (true) {
            interactionManager.showMenuItemInCart(cart);

            var userSelected = interactionManager.read();
            var orderMenu = ORDER_MENU.values()[userSelected];

            switch (orderMenu) {
                case ORDER -> {
                    interactionManager.showDiscountSelectMessage();
                    var totalPrice = cart.calculateDiscountedPrice(interactionManager.read());
                    if (totalPrice < 0) {
                        interactionManager.showError();
                        break;
                    }
                    interactionManager.showOrderDoneMessage(totalPrice);
                    cart.clearCart();
                    return;
                }
                case REMOVE_CART_ITEM -> {
                    interactionManager.showMessage("취소할 메뉴의 번호를 입력해주세요 : ");
                    if (!cart.removeItemByIndex(interactionManager.read()))
                        interactionManager.showMessage("잘못된 명령입니다.\n");
                    return;
                }
                case BACK_TO_MAIN -> { return; }
                default -> interactionManager.showMessage("잘못된 명령입니다.\n");
            }
        }
    }

    private boolean isCartMenuSelected(int selected) {
        return !cart.isEmpty() && (isOrder(selected) || isCancelOrder(selected));
    }


    private boolean isOrder(int selected) {
        return selected == menuManager.getAllMenu().size() + 1;
    }

    private boolean isCancelOrder(int selected) {
        return selected == menuManager.getAllMenu().size() + 2;
    }

    private boolean isExit(int index) {
        return index == -1;
    }
}