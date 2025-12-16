package entity.animals;

import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;

public class Dog extends Animal{
    private SimpleStringProperty race;

    public Dog() {
        super(AnimalType.DOG);
        this.race = new SimpleStringProperty();
    }

    @Override
    public String specificAttributes() {
        return getRace();
    }

    public Dog(String name, String color, String description, String imageFileLocation, String race) throws IOException {
        super(name, color, description, imageFileLocation, AnimalType.DOG);
        this.race = new SimpleStringProperty(race);
    }

    public Dog(String name, String color, String description, String race) throws IOException {
        super(name, color, description, AnimalType.DOG);
        this.race = new SimpleStringProperty(race);
    }

    public String getRace() {
        return race.get();
    }

    public SimpleStringProperty raceProperty() {
        return race;
    }

    public void setRace(String race) {
        this.race.set(race);
    }

    @Override
    public String toString() {
        return "Dog{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", color=" + getColor() +
                ", description=" + getDescription() +
                ", imageFile=" + getImageFileLocation() +
                ", race=" + race +
                '}';
    }
}
