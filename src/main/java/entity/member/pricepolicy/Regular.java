package entity.member.pricepolicy;

public class Regular implements PricePolicy{
    public Regular() {
    }

    @Override
    public double applyDiscount(double amount) {
        return amount;
    }

    @Override
    public double getFee() {
        return 0;
    }
}
