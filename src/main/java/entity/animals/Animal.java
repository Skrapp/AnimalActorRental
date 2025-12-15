package entity.animals;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import entity.IDCreator;
import javafx.beans.property.SimpleStringProperty;

import java.io.File;
import java.io.IOException;
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
    private String imageFileLocation;
    private Type type;
    private boolean available = true;

    public Animal() {
        this.id = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.color = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
    }

    public Animal(Type type) {
        this();
        this.type = type;
    }

    public Animal(String name, String color, String description, String imageFileLocation, Type type) throws IOException {
        this.id = new SimpleStringProperty("A".concat(String.valueOf(IDCreator.getInstance().getNextId())));
        this.name = new SimpleStringProperty(name);
        this.color = new SimpleStringProperty(color);
        this.description = new SimpleStringProperty(description);
        this.imageFileLocation = imageFileLocation;
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

    public abstract String specificAttributes();

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

    public String getImageFileLocation() {
        return imageFileLocation;
    }

    public void setImageFileLocation(String imageFileLocation) {
        this.imageFileLocation = imageFileLocation;
    }

    public Type getType() {
        return type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }



    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name=" + name +
                ", color=" + color +
                ", description=" + description +
                ", imageFile=" + imageFileLocation +
                '}';
    }
}
