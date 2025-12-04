package entity.member.pricepolicy;

public class Student implements PricePolicy{
    private double priceReduction;
    private String name;

    public Student() {
        name = "Student";
        priceReduction = 400;
    }

    public double getPriceReduction() {
        return priceReduction;
    }

    public void setPriceReduction(double priceReduction) {
        this.priceReduction = priceReduction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returnerar nytt pris. Om kostnaden med rabatten är under 400 kr returneras 400 kr, vilket gör att de inte går att
     * hyra djur under 400 kr.
     * @param amount originalpris
     * @return reducerat pris
     */
    @Override
    public double applyDiscount(double amount) {
        double reducedAmount = amount -priceReduction;
        return (reducedAmount >= 400) ? reducedAmount : 400;
    }

    @Override
    public double getFee() {
        return 0;
    }
}
