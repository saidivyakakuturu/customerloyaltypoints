package org.retailer.demo.domain.enums;

public enum PointAmounts {
    FIRST_POINTS(50),
    SECOND_POINTS(100);

    private final double amount;

    PointAmounts(final double amount) {
        this.amount = amount;
    }

    public double getValue() { return amount; }
}
