package entity.member;

import dao.Rental;
import entity.IDCreator;
import entity.member.pricepolicy.PricePolicy;

import java.util.List;

public class Member {
    private String id;
    private String name;
    private PricePolicy level;
    private List<Rental> rentalHistory;
    private int numberOfProductions;

    public Member() {
    }

    public Member(String name, PricePolicy level, int numberOfProductions) {
        this.id = "M".concat(Integer.toString(IDCreator.getInstance().getNextId()));
        this.name = name;
        this.level = level;
        this.numberOfProductions = numberOfProductions;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PricePolicy getLevel() {
        return level;
    }

    public void setLevel(PricePolicy level) {
        this.level = level;
    }

    public List<Rental> getRentalHistory() {
        return rentalHistory;
    }

    public void setRentalHistory(List<Rental> rentalHistory) {
        this.rentalHistory = rentalHistory;
    }

    public int getNumberOfProductions() {
        return numberOfProductions;
    }

    public void setNumberOfProductions(int numberOfProductions) {
        this.numberOfProductions = numberOfProductions;
    }
}
