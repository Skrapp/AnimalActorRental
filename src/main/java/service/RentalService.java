package service;

import dao.Rental;
import entity.animals.Animal;
import entity.member.Member;
import exceptions.AnimalNotFoundException;
import exceptions.MemberNotFoundException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RentalService {
    private double income;
    private MemberService memberService;
    private AnimalService animalService;

    public RentalService(double income) {
        this.income = income;
    }

    public RentalService(MemberService memberService, AnimalService animalService) {
        this();
        this.memberService = memberService;
        this.animalService = animalService;
    }

    public RentalService(double income, MemberService memberService, AnimalService animalService) {
        this.income = income;
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

    public List<Animal> returnAnimals(Member member, List<Rental> rentalsToReturn) throws AnimalNotFoundException, IOException, MemberNotFoundException {
        List<Animal> animalsReturned = new ArrayList<>();
        for(Rental rental : rentalsToReturn){
            if(rental.isReturned()){
                continue;
            }
            Animal animal = rental.getAnimal();
            rental.setReturned(true);
            animal.setAvailable(true);
            animalService.updateAnimal(animal);
            animalsReturned.add(animal);
        }
        memberService.updateMember(member);
        return animalsReturned;
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
