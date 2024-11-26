import menu.data.Menu;
import menu.data.MenuItem;

import java.util.List;
import java.util.Scanner;

import static java.lang.System.in;

// 당장은 scanner 를 사용하지만, CLI 와 GUI 의 선택에 따라 scanner 를 사용하지 않을 수 있습니다.
// 이에 InteractionManager 로 분리하여 따로 관리하도록 하였습니다.
public class InteractionManager {
    private final Scanner scanner = new Scanner(in);

    public void showError() {
        System.out.println("잘못된 명령입니다. 다시 입력하여 주십시오.\n");
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public int read() {
        try {
            var userSelected = Integer.parseInt(scanner.nextLine());
            if (userSelected == 0)
                return 0;
            else if (userSelected < 0)
                return -1;
            else return userSelected;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void showMainMenu(List<Menu> menu) {
        var title = " [ MAIN MENU ]\n";
        var menuInfo = Parser.parseMenuNamesToString(menu);
        var exitMenu = "0. 종료\t";
        System.out.println(title + menuInfo + exitMenu);
    }

    public void showDiscountSelectMessage() {
        System.out.println("할인 정보를 입력해주세요\n");

        for (int i = 0; i < DISCOUNT.values().length; i++) {
            DISCOUNT discount = DISCOUNT.values()[i];
            var name = discount.name();
            var discountRate = (int) (discount.getDiscountRate() * 100);
            System.out.println(i + ". " + name + " : " + discountRate + "%");
        }
    }

    public void showOrderDoneMessage(int price) {
        System.out.println("\n주문이 완료되었습니다. 금액은 W " + price + " 입니다.\n");
    }

    public void showMenuItemInCart(Cart cart) {
        var title = "아래와 같이 주문하시겠습니까?\n\n [ ORDERS ]\n";
        var orderedItemsString = new StringBuilder(title);

        final int[] index = {1};

        cart.getItems().forEach((name, count) -> {
            orderedItemsString.append(index[0])
                    .append(". ")
                    .append(name)
                    .append(" : ")
                    .append(count)
                    .append(" 개\n");
            index[0]++;
        });

        var priceTitle = "\n [ TOTAL ]\n W ";
        var orderMenu = "\n\n1. 주문\n2. 메뉴 취소\n3. 메뉴판";
        orderedItemsString.append(priceTitle)
                .append(cart.calculateTotal())
                .append(orderMenu);

        System.out.println(orderedItemsString);
    }

    public void showOrderMenu(int menuIndex) {
        var title = " [ ORDER MENU ]\n";
        int orderMenuNumber = menuIndex + 1;
        int cancelMenuNumber = menuIndex + 2;
        String orderMenuString = orderMenuNumber + ". ORDERS\t| 장바구니를 확인 후 주문합니다.\n";
        String cancelMenuString = cancelMenuNumber + ". CANCEL\t| 장바구니를 비웁니다.\n";
        System.out.println(title + orderMenuString + cancelMenuString);
    }

    public void showTargetMenu(Menu menu) {
        var title = " [ " + menu.getName().toUpperCase() + " ]\n";
        var menuItemsInfo = Parser.parseMenuToString(menu);
        System.out.println(title + menuItemsInfo);
    }

    public void showTargetMenuItem(MenuItem menuItem) {
        String prefix = "선택한 메뉴\n";
        var menuItemInfo = Parser.parseMenuitemToString(menuItem);
        System.out.println(prefix + menuItemInfo);
    }

    public void showPutInCart(MenuItem menuItem) {
        String suffix = "위 메뉴를 장바구니에 추가하시겠습니까?\n1. 확인\t2.취소\n";
        var info = " [ " + menuItem.getName() + " ] \n";
        System.out.println(info + suffix);
    }

    public void off() {
        System.out.println("프로그램을 종료합니다.");
        scanner.close();
    }
}
