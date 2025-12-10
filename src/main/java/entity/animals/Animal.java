package entity.animals;

import java.io.File;
import java.util.List;

public abstract class Animal {
    private String id;
    private String name;
    private String color;
    private String description;
    private List<Trick> tricks;
    private File imageFile;
}
