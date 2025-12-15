package entity.animals;

import java.io.File;
import java.io.IOException;

public class Horse extends Animal{
    private boolean pony;

    public Horse() {
        super(Type.HORSE);
    }

    @Override
    public String specificAttributes() {
        return pony ? "Är av ponnymodell." : "";
    }

    public Horse(String name, String color, String description, File imageFile, boolean pony) throws IOException {
        super(name, color, description, imageFile, Type.HORSE);
        this.pony = pony;
    }

    public Horse(String name, String color, String description, boolean pony) throws IOException {
        super(name, color, description, Type.HORSE);
        this.pony = pony;
    }

    public boolean isPony() {
        return pony;
    }

    public void setPony(boolean pony) {
        this.pony = pony;
    }
}
