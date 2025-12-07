package entity.member.pricepolicy;

public class Golden implements PricePolicy{
    private double procentReduction;
    private double fee;

    public Golden() {
        procentReduction = 0.7;
        fee = 3000;
    }

    public double getProcentReduction() {
        return procentReduction;
    }

    public void setProcentReduction(double procentReduction) {
        this.procentReduction = procentReduction;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public double getFee() {
        return fee;
    }

    @Override
    public double applyDiscount(double amount) {
        return amount * procentReduction;
    }

    @Override
    public double payFee() {
        return 0;
    }
}
