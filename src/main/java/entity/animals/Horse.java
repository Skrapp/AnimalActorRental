package entity.animals;

import java.io.IOException;

public class Horse extends Animal{
    private boolean pony;

    public Horse() {
        super(AnimalType.HORSE);
    }

    @Override
    public String specificAttributes() {
        return pony ? "Är av ponnymodell." : "";
    }

    public Horse(String name, String color, String description, String imageFileLocation, boolean pony) throws IOException {
        super(name, color, description, imageFileLocation, AnimalType.HORSE);
        this.pony = pony;
    }

    public Horse(String name, String color, String description, boolean pony) throws IOException {
        super(name, color, description, AnimalType.HORSE);
        this.pony = pony;
    }

    public boolean isPony() {
        return pony;
    }

    public void setPony(boolean pony) {
        this.pony = pony;
    }

    @Override
    public String toString() {
        return "Horse{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", color=" + getColor() +
                ", description=" + getDescription() +
                ", imageFile=" + getImageFileLocation() +
                ", pony=" + pony +
                '}';
    }
}
