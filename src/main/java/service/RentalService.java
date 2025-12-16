package service;

import dao.Rental;
import entity.animals.Animal;
import entity.member.Member;

import java.time.LocalDate;
import java.util.List;

public class RentalService {
    public Member rentAnimal(Member member, Animal animal, LocalDate dateFrom, LocalDate dateTo, double price){
        List<Rental> rentals = member.getRentalHistory();
        rentals.add(new Rental(animal, dateFrom, dateFrom, price));
        member.setRentalHistory(rentals);
        return member;
    }
}
