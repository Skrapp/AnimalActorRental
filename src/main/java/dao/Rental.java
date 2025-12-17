package dao;

import entity.animals.Animal;

import java.time.LocalDate;

public class Rental {
    private Animal animal;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private double price;
    private boolean returned;

    public Rental() {
    }

    public Rental(Animal animal, LocalDate dateFrom, LocalDate dateTo, double price) {
        this.animal = animal;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.price = price;
        this.returned = false;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "animal=" + animal +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", price=" + price +
                ", returned=" + returned +
                '}';
    }
}
