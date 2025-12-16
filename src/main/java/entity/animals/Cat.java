package entity.animals;

import java.io.IOException;

public class Cat extends Animal{
    private boolean exotic;
    public Cat() {
        super(AnimalType.CAT);
    }

    @Override
    public String specificAttributes() {
        return exotic ? "Är ett tämjt vilddjur." : "Är en domesticerad katt.";
    }

    public Cat(String name, String color, String description, String imageFileLocation, double price, boolean exotic) throws IOException {
        super(name, color, description, imageFileLocation, price, AnimalType.CAT);
        this.exotic = exotic;
    }

    public boolean isExotic() {
        return exotic;
    }

    public void setExotic(boolean exotic) {
        this.exotic = exotic;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", color=" + getColor() +
                ", description=" + getDescription() +
                ", imageFile=" + getImageFileLocation() +
                ", exotic=" + exotic +
                '}';
    }
}
