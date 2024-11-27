public enum ORDER_MENU {
    NONE(0, ""),
    ORDER(1, "주문"),
    REMOVE_CART_ITEM(2, "메뉴 취소"),
    BACK_TO_MAIN(3, "메뉴판");

    private final int orderMenu;
    private final String displayName;

    ORDER_MENU(int menu, String displayName) {
        this.orderMenu = menu;
        this.displayName = displayName;
    }

    public int getOrderMenu() {
        return orderMenu;
    }

    public String getDisplayName() {
        return displayName;
    }
}