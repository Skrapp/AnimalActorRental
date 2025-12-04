package entity.member.pricepolicy;

public class Regular implements PricePolicy{
    private String name;
    public Regular() {
        name = "Regular";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
