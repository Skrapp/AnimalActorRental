package dao;

import entity.animals.Animal;
import entity.member.Member;

import java.time.LocalDate;

public class Rental {
    private Animal animal;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private double price;

    public Rental() {
    }

    public Rental(Animal animal, LocalDate dateFrom, LocalDate dateTo, double price) {
        this.animal = animal;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.price = price;
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
}
