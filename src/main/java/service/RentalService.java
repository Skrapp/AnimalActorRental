package service;

import dao.Rental;
import entity.animals.Animal;
import entity.member.Member;
import exceptions.AnimalNotFoundException;
import exceptions.MemberNotFoundException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class RentalService {
    private double income;
    private MemberService memberService;
    private AnimalService animalService;

    public RentalService(double income) {
        this.income = income;
    }

    public RentalService(MemberService memberService, AnimalService animalService) {
        this.memberService = memberService;
        this.animalService = animalService;
    }

    public RentalService() {
        income = 0;
    }

    public void rentAnimal(Member member, Animal animal, LocalDate dateFrom, LocalDate dateTo, double price) throws IOException, MemberNotFoundException, AnimalNotFoundException {
        List<Rental> rentals = member.getRentalHistory();
        rentals.add(new Rental(animal, dateFrom, dateTo, price));
        animal.setAvailable(false);
        member.setRentalHistory(rentals);
        memberService.updateMember(member);
        animalService.updateAnimal(animal);
    }

    public double receivePayment(double amount){
        return income += amount;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }
}
