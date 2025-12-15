package entity.animals;

import java.io.File;
import java.io.IOException;

public class Bird extends Animal{
    private boolean flying;
    
    public Bird() {
        super(Type.BIRD);
    }

    @Override
    public String specificAttributes() {
        return flying ? "Kan flyga." : "Kan inte flyga.";
    }

    public Bird(String name, String color, String description, File imageFile, boolean flying) throws IOException {
        super(name, color, description, imageFile, Type.BIRD);
        this.flying = flying;
    }

    public Bird(String name, String color, String description, boolean flying) throws IOException {
        super(name, color, description, Type.BIRD);
        this.flying = flying;
    }

    public boolean isFlying() {
        return flying;
    }

    public void setFlying(boolean flying) {
        this.flying = flying;
    }
}
