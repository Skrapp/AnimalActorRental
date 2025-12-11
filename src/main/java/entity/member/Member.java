package entity.member;

import dao.Rental;
import entity.IDCreator;
import entity.member.pricepolicy.PricePolicy;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Member {
    private SimpleStringProperty id;
    private SimpleStringProperty name;
    private ObjectProperty<PricePolicy> level;
    private List<Rental> rentalHistory;
    private SimpleIntegerProperty productions;

    public Member() {
        this.id = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.level = new SimpleObjectProperty<>();
        rentalHistory = new ArrayList<>();
        this.productions = new SimpleIntegerProperty();
    }

    public Member(String name, PricePolicy level, int productions) throws IOException {
        this.id = new SimpleStringProperty("M".concat(Integer.toString(IDCreator.getInstance().getNextId())));
        this.name = new SimpleStringProperty(name);
        this.level = new SimpleObjectProperty<>(level);
        this.rentalHistory = new ArrayList<>();
        this.productions = new SimpleIntegerProperty(productions);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public SimpleStringProperty idProperty(){
        return id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty(){
        return name;
    }

    public PricePolicy getLevel() {
        return level.get();
    }

    public void setLevel(PricePolicy level) {
        this.level.set(level);
    }

    public ObjectProperty<PricePolicy> levelProperty(){
        return level;
    }

    public List<Rental> getRentalHistory() {
        return rentalHistory;
    }

    public void setRentalHistory(List<Rental> rentalHistory) {
        this.rentalHistory = rentalHistory;
    }

    public int getProductions() {
        return productions.get();
    }

    public void setProductions(int productions) {
        this.productions.set(productions);
    }

    public SimpleIntegerProperty productionsProperty(){
        return productions;
    }



    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", level=" + level.get() +
                ", rentalHistory=" + rentalHistory +
                ", numberOfProductions=" + productions +
                '}';
    }
}
