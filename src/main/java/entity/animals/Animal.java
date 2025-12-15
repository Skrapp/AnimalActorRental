package entity.animals;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import entity.IDCreator;
import javafx.beans.property.SimpleStringProperty;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * För att kunna se vilken klass som används i JSON
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Bird.class, name = "BIRD"),
        @JsonSubTypes.Type(value = Cat.class, name = "CAT"),
        @JsonSubTypes.Type(value = Dog.class, name = "DOG"),
        @JsonSubTypes.Type(value = Horse.class, name = "HORSE")
})

public abstract class Animal {
    private SimpleStringProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty color;
    private SimpleStringProperty description;
    private File imageFile;
    private Type type;

    public Animal() {
        this.id = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.color = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
    }

    public Animal(Type type) {
        this.type = type;
        this.id = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.color = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
    }

    public Animal(String name, String color, String description, File imageFile, Type type) throws IOException {
        this.id = new SimpleStringProperty("A".concat(String.valueOf(IDCreator.getInstance().getNextId())));
        this.name = new SimpleStringProperty(name);
        this.color = new SimpleStringProperty(color);
        this.description = new SimpleStringProperty(description);
        this.imageFile = imageFile;
        this.type = type;
    }

    public Animal( String name, String color, String description, Type type) throws IOException {
        this.id = new SimpleStringProperty("A".concat(String.valueOf(IDCreator.getInstance().getNextId())));;
        this.name = new SimpleStringProperty(name);
        this.color = new SimpleStringProperty(color);
        this.description = new SimpleStringProperty(description);
        this.type = type;
    }

    public static List<String> getAllAnimalTypes(){
        return Arrays.asList("Fågel", "Katt", "Hund", "Häst");
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getColor() {
        return color.get();
    }

    public SimpleStringProperty colorProperty() {
        return color;
    }

    public void setColor(String color) {
        this.color.set(color);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name=" + name +
                ", color=" + color +
                ", description=" + description +
                ", imageFile=" + imageFile +
                '}';
    }
}
