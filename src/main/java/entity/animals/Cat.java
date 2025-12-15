package entity.animals;

import java.io.File;
import java.io.IOException;

public class Cat extends Animal{
    private boolean exotic;
    public Cat() {
        super(Type.CAT);
    }

    public Cat(String name, String color, String description, File imageFile, boolean exotic) throws IOException {
        super(name, color, description, imageFile, Type.CAT);
        this.exotic = exotic;
    }

    public Cat(String name, String color, String description, boolean exotic) throws IOException {
        super(name, color, description, Type.CAT);
        this.exotic = exotic;
    }

    public boolean isExotic() {
        return exotic;
    }

    public void setExotic(boolean exotic) {
        this.exotic = exotic;
    }
}
