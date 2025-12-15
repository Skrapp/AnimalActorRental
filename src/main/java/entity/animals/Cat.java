package entity.animals;

import java.io.File;
import java.io.IOException;

public class Cat extends Animal{
    private boolean exotic;
    public Cat() {
        super(Type.CAT);
    }

    @Override
    public String specificAttributes() {
        return exotic ? "Är ett tämjt vilddjur." : "Är en domesticerad katt.";
    }

    public Cat(String name, String color, String description, String imageFileLocation, boolean exotic) throws IOException {
        super(name, color, description, imageFileLocation, Type.CAT);
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
