package service;

import dao.AnimalRegistry;
import entity.animals.Animal;
import exceptions.AnimalNotFoundException;

import java.io.IOException;
import java.util.List;

public class AnimalService {
    private AnimalRegistry animalRegistry = new AnimalRegistry("animals");

    public boolean addAnimal(Animal animal) throws IOException {
        animalRegistry.addAnimal(animal);
        return true;
    }



    /*public void removeAnimal(Animal animalToRemove) throws IOException, AnimalNotFoundException {
        List<String> animalsId = new ArrayList<>();
        animalsId.add(animalToRemove.getId());
        animalRegistry.removeAnimalByID(animalsId);
    }*/

   /* public void removeAnimals(List<Animal> animals) throws IOException, AnimalNotFoundException {
        List<String> animalsId =
                animals.stream()
                        .map(Animal::getId)
                        .collect(Collectors.toList());
        animalRegistry.removeAnimalByID(animalsId);
    }*/

    /*public void updateAnimal(Animal animal) throws IOException, AnimalNotFoundException {
        removeAnimal(animal);
        addAnimal(animal);
    }*/

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
     * @param searchWord         filtrerar enligt sökord på namn eller id
     * @param pricePolicyClasses filtrera enligt pricePolicy. För att inkludera alla PricePolicy använd pricePolicy.class
     * @return returnerar set med medlemmar
     * @throws IOException Om fil inte kan läsas kastas exception
     *//*
    public Set<Animal> getFilteredAnimals(String searchWord, Class<? extends Animal>)
            throws IOException {
        Set<Animal> animalsSinglePricePolicy;
        Set<Animal> animals = new HashSet<>();
        for(Class<? extends PricePolicy> pricePolicyClass : pricePolicyClasses){
            animalsSinglePricePolicy = animalRegistry.getAnimals().stream()
                    .filter(m -> (pricePolicyClass.isInstance(m.getLevel())
                            && (m.getName().toLowerCase().contains(searchWord.toLowerCase())
                            || m.getId().toLowerCase().contains(searchWord.toLowerCase()))
                            && m.getProductions() >= minProductions && m.getProductions() <= maxProductions))
                    .collect(Collectors.toCollection(HashSet::new));
            animals.addAll(animalsSinglePricePolicy);
        }
        return animals;
    }

    *//**
     * TODO Fixa så att ifall minProduction eller maxProduction är tomma ska det inte räknas
     * @param searchWord
     * @param pricePolicyClasses
     * @return
     * @throws IOException
     *//*
    public Set<Animal> getFilteredAnimals(String searchWord, List<Class<? extends PricePolicy>> pricePolicyClasses)
            throws IOException {
        Set<Animal> animalsSinglePricePolicy;
        Set<Animal> animals = new HashSet<>();
        for(Class<? extends PricePolicy> pricePolicyClass : pricePolicyClasses){
            animalsSinglePricePolicy = animalRegistry.getAnimals().stream()
                    .filter(m -> (pricePolicyClass.isInstance(m.getLevel())
                            && (m.getName().toLowerCase().contains(searchWord.toLowerCase())
                            || m.getId().toLowerCase().contains(searchWord.toLowerCase()))))
                    .collect(Collectors.toCollection(HashSet::new));
            animals.addAll(animalsSinglePricePolicy);
        }
        return animals;
    }*/
}
