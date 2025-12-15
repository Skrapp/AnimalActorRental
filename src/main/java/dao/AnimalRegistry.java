package dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entity.animals.Animal;

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
        return Stream.of(new File(directory).listFiles())
                .filter(f -> !f.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }

    /*public void removeAnimalByID(List<String> ids) throws IOException, AnimalNotFoundException {
        List<Animal> animals = getAnimals();
        //Om det inte finns någon djur med givet id så kan det inte tas bort, därav är det troligtvis fel någonstans
        for(String id : ids) {
            if (!animals.removeIf(m -> m.getId().equals(id))) {
                throw new AnimalNotFoundException("Djur med id \"" + id + "\" finns inte i djurregister.");
            }
        }
        reloadFile(animals);
    }*/

    public boolean addAnimal(Animal animal) throws IOException{
        Class<? extends Animal> animalClass = animal.getClass();
        File animalFile = new File(directory.concat("\\").concat(animalClass.getSimpleName().toLowerCase()).concat(".json"));
        List<Animal> animals = getAnimals(animalFile);
        animals.add(animal);
        reloadFile(animalFile, animals);
        return true;
    }

    public List<Animal> getAllAnimals() throws IOException {
        List <Animal> animals = new ArrayList<>();
        for(String fileName : fileNames){
            animals.addAll(getAnimals(new File(fileName)));
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
