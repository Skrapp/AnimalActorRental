package entity.member;

import dao.Rental;
import entity.IDCreator;
import entity.member.pricepolicy.PricePolicy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Member {
    private String id;
    private String name;
    private PricePolicy level;
    private List<Rental> rentalHistory;
    private int productions;

    public Member() {
    }

    public Member(String name, PricePolicy level, int productions) throws IOException {
        this.id = "M".concat(Integer.toString(IDCreator.getInstance().getNextId()));
        this.name = name;
        this.level = level;
        this.rentalHistory = new ArrayList<>();
        this.productions = productions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getProductions() {
        return productions;
    }

    public void setProductions(int productions) {
        this.productions = productions;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", rentalHistory=" + rentalHistory +
                ", numberOfProductions=" + productions +
                '}';
    }
}
