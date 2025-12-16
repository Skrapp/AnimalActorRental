package entity.animals;

import java.io.IOException;

public class Bird extends Animal{
    private boolean flying;
    
    public Bird() {
        super(AnimalType.BIRD);
    }

    @Override
    public String specificAttributes() {
        return flying ? "Kan flyga." : "Kan inte flyga.";
    }

    public Bird(String name, String color, String description, String imageFileLocation, boolean flying) throws IOException {
        super(name, color, description, imageFileLocation, AnimalType.BIRD);
        this.flying = flying;
    }

    public Bird(String name, String color, String description, boolean flying) throws IOException {
        super(name, color, description, AnimalType.BIRD);
        this.flying = flying;
    }

    public boolean isFlying() {
        return flying;
    }

    public void setFlying(boolean flying) {
        this.flying = flying;
    }

    @Override
    public String toString() {
        return "Bird{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", color=" + getColor() +
                ", description=" + getDescription() +
                ", imageFile=" + getImageFileLocation() +
                ", flying=" + flying +
                '}';
    }
}
