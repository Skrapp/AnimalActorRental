package service;

import dao.AnimalRegistry;
import entity.animals.Animal;
import entity.animals.AnimalType;
import exceptions.AnimalNotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        removeAnimal(animal);
        addAnimal(animal);
    }

    public Animal getAnimalByID(String id) throws IOException, AnimalNotFoundException {
        List<Animal> allAnimals = getAllAnimals();
        for(Animal animal : allAnimals){
            if(animal.getId().equals(id)){
                return animal;
            }
        }
        throw new AnimalNotFoundException("Kunde inte hitta id \"" + id + "\" i djurregister.");
    }

    public List<Animal> getAllAnimals() throws IOException {
        return animalRegistry.getAllAnimals();
    }

    /**
     * Filtrerar och sorterar djur från fil.
     *
     * @param searchWord filtrerar enligt sökord på id, namn, färg eller beskrivning
     * @param animalClass klass att filtrera. Använd superklassen Animal för att filtrera från alla djur.
     * @return returnerar set med medlemmar
     * @throws IOException Om fil inte kan läsas kastas exception
     */
    public List<Animal> getFilteredAnimals(String searchWord, Class<? extends Animal> animalClass)
            throws IOException {
        List<Animal> animals = animalRegistry.getAllAnimals().stream()
                .filter(a -> (animalClass.isInstance(a))
                        && (a.getName().toLowerCase().contains(searchWord.toLowerCase())
                        || a.getId().toLowerCase().contains(searchWord.toLowerCase())
                        || a.getColor().toLowerCase().contains(searchWord.toLowerCase())
                        || a.getDescription().toLowerCase().contains(searchWord.toLowerCase())))
                .collect(Collectors.toList());
        return animals;
    }
}
