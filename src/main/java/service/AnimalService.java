package service;

import dao.AnimalRegistry;
import entity.animals.Animal;
import entity.animals.AnimalType;
import exceptions.AnimalNotFoundException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class AnimalService {
    private AnimalRegistry animalRegistry = new AnimalRegistry("animals");

    public boolean addAnimal(Animal animal) throws IOException {
        animalRegistry.addAnimal(animal);
        return true;
    }



    public void removeAnimal(Animal animalToRemove) throws IOException, AnimalNotFoundException {
        animalRegistry.removeAnimalByID(animalToRemove.getId(), animalToRemove.getAnimalType());
    }

    public void updateAnimal(Animal animal) throws IOException, AnimalNotFoundException {
        animalRegistry.updateAnimal(animal);
    }

    public Animal getAnimalByID(String id) throws IOException, AnimalNotFoundException {
        Set<Animal> allAnimals = getAllAnimals();
        for(Animal animal : allAnimals){
            if(animal.getId().equals(id)){
                return animal;
            }
        }
        throw new AnimalNotFoundException("Kunde inte hitta id \"" + id + "\" i djurregister.");
    }

    public Set<Animal> getAllAnimals() throws IOException {
        return getFilteredAnimals("", null);
    }

    /**
     * Filtrerar och sorterar djur från fil.
     * @param searchWord filtrerar enligt sökord på id, namn, färg eller beskrivning
     * @param animalType djurtyp att filtrera. Använd null för att filtrera från alla djurtyper.
     * @return returnerar set med djur
     * @throws IOException Om fil inte kan läsas kastas exception
     */
    public Set<Animal> getFilteredAnimals(String searchWord, AnimalType animalType)
            throws IOException {
        TreeSet<Animal> animals = animalRegistry.getAllAnimals().stream()
                .filter(a -> (animalType == null || animalType.equals(a.getAnimalType()))
                        && (a.getName().toLowerCase().contains(searchWord.toLowerCase())
                        || a.getId().toLowerCase().contains(searchWord.toLowerCase())
                        || a.getColor().toLowerCase().contains(searchWord.toLowerCase())
                        || a.getDescription().toLowerCase().contains(searchWord.toLowerCase())))
                .collect(
                        Collectors.toCollection(()
                                -> new TreeSet<>(Comparator.comparing(Animal::getName)))
                );
        return animals;
    }
}
