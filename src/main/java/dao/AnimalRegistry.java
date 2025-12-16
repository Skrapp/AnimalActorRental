package dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entity.animals.Animal;
import entity.animals.AnimalType;
import exceptions.AnimalNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnimalRegistry {
    //TODO Skriv om alla metoder så det ser först efter vilken fil de ska se efter
    private Set<String> fileNames;
    private ObjectMapper mapper = new ObjectMapper();
    private String directory;

    public AnimalRegistry(String directory) {
        this.directory = directory;
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        fileNames = getCurrentFileNames();
    }

    private Set<String> getCurrentFileNames() {
        //TODO om animals mappen är tom ska Set vara tomt, just nu blir det en bugg
        return Stream.of(new File(directory).listFiles())
                .filter(f -> !f.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }

    public void removeAnimalByID(String id, AnimalType type) throws IOException, AnimalNotFoundException {
        File animalFile = new File(directory.concat(File.separator).concat(type.name().toLowerCase()).concat(".json"));
        List<Animal> animals = getAnimals(animalFile);
        //Om det inte finns någon djur med givet id så kan det inte tas bort, därav är det troligtvis fel någonstans
        if (!animals.removeIf(a -> a.getId().equals(id))) {
            throw new AnimalNotFoundException("Djur med id \"" + id + "\" finns inte i djurregister.");
        }

        reloadFile(animalFile, animals);
        System.out.println(id +" är borttagen.");
    }

    public boolean addAnimal(Animal animal) throws IOException{
        String animalType = animal.getAnimalType().name().toLowerCase();
        File animalFile = new File(directory.concat(File.separator).concat(animalType).concat(".json"));
        List<Animal> animals = getAnimals(animalFile);
        animals.add(animal);
        reloadFile(animalFile, animals);
        return true;
    }

    public List<Animal> getAllAnimals() throws IOException {
        List <Animal> animals = new ArrayList<>();
        for(String fileName : fileNames){
            animals.addAll(getAnimals(new File(directory.concat(File.separator).concat(fileName))));
        }
        return animals;
    }

    public List<Animal> getAnimals(File animalFile) throws IOException {
        if (!animalFile.exists() || animalFile.length() == 0) {
            return new ArrayList<>();
        }

        return new ArrayList<>(Arrays.asList(mapper.readValue(animalFile, Animal[].class)));
    }

    public void reloadFile(File animalFile, List<? extends Animal> animals) throws IOException{
        try {
            mapper.writeValue(animalFile, animals);
        } catch (IOException e) {
            throw new IOException("Kan inte läsa fil.\n" + e);
        }
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }
}
