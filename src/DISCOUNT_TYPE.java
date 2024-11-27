public enum DISCOUNT_TYPE {
    일반(0.0),
    군인(0.5),
    학생(0.6),
    튜터(0.95),
    CEO(-1.6);

    private final double discountRate;

    DISCOUNT_TYPE(double discountRate) {
        this.discountRate = discountRate;
    }

    public double getDiscountRate() {
        return discountRate;
    }
}