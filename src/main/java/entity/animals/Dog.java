package entity.animals;

import javafx.beans.property.SimpleStringProperty;

import java.io.File;
import java.io.IOException;

public class Dog extends Animal{
    private SimpleStringProperty race;

    public Dog() {
        super(Type.DOG);
        this.race = new SimpleStringProperty();
    }

    public Dog(String name, String color, String description, File imageFile, String race) throws IOException {
        super(name, color, description, imageFile, Type.DOG);
        this.race = new SimpleStringProperty(race);
    }

    public Dog(String name, String color, String description, String race) throws IOException {
        super(name, color, description, Type.DOG);
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
}
