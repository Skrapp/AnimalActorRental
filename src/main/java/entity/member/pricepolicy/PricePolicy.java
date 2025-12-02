package entity.member.pricepolicy;

public interface PricePolicy {
    double applyDiscount(double amount);
    double getFee();
}
