package entity.member.pricepolicy;

public class Golden implements PricePolicy{
    private double procentReduction;
    private double fee;
    private String name;

    public Golden() {
        name = "Golden";
        procentReduction = 0.7;
        fee = 3000;
    }

    public double getProcentReduction() {
        return procentReduction;
    }

    public void setProcentReduction(double procentReduction) {
        this.procentReduction = procentReduction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    @Override
    public double applyDiscount(double amount) {
        return amount * procentReduction;
    }

    @Override
    public double getFee() {
        return 0;
    }
}
