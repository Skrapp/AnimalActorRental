package gui;

import entity.animals.*;
import exceptions.AnimalNotFoundException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import service.AnimalService;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class EditAnimal {
    private final SceneManager sceneManager;
    private final AnimalService animalService;
    private final Animal animal;

    private TextField nameField;
    private TextArea descriptionField;
    private TextField colorField;
    private AtomicReference<String> imageFileLocation;
    //private CheckBox availableCheckBox;
    private CheckBox flyingCheckBox;
    private CheckBox exoticCheckBox;
    private TextField raceField;
    private CheckBox ponyCheckBox;

    public EditAnimal(SceneManager sceneManager, AnimalService animalService, Animal animal) {
        this.sceneManager = sceneManager;
        this.animalService = animalService;
        this.animal = animal;
    }

    public Parent start(){
        //Skapar och placerar nodes
        Label titleLabel = new Label("Ändra " + animal.getAnimalType().getSwedish().toLowerCase());
        Label nameLabel = new Label("Namn");
        nameField = new TextField(animal.getName());
        Label descriptionLabel = new Label("Beskrivning");
        descriptionField = new TextArea(animal.getDescription());
        descriptionField.setWrapText(true);
        descriptionField.setPrefWidth(500-40*2);
        Label colorLabel = new Label("Färg");
        colorField = new TextField(animal.getColor());
        /*Label availableLabel = new Label("Checka denna om " + animal.getName() + " är tillgänglig.");
        availableCheckBox = new CheckBox("Tillgänglig");*/

        Button toListButton = new Button("Se alla djur");

        //Lägg till bild, öppnar utforskaren (för Windows)
        Button addImageButton = new Button("Lägg till bild");
        Label choosenImageLabel = new Label(animal.getImageFileLocation() == null ? "Ingen bild vald" : animal.getImageFileLocation());
        imageFileLocation = new AtomicReference<>(animal.getImageFileLocation() == null ? "" : animal.getImageFileLocation());

        addImageButton.setOnAction(e -> {
            File file = ImageManager.chooseImage(addImageButton);
            if (file != null) {
                imageFileLocation.set(file.getAbsolutePath());
                choosenImageLabel.setText(imageFileLocation.get());
            }
        });

        VBox nameBox = new VBox(nameLabel, nameField);
        VBox colorBox = new VBox(colorLabel, colorField);
        VBox imageBox = new VBox(addImageButton, choosenImageLabel);
        VBox descriptionBox = new VBox(descriptionLabel, descriptionField);

        VBox formBox = new VBox(10, nameBox, colorBox, imageBox, descriptionBox);
        formBox.setAlignment(Pos.CENTER_RIGHT);

        switch (animal.getAnimalType()){
            case BIRD:
                Label flyingLabel = new Label("kan fågeln flyga?");
                flyingCheckBox = new CheckBox("Ja");
                flyingCheckBox.setSelected(((Bird)animal).isFlying());
                VBox birdBox = new VBox(flyingLabel, flyingCheckBox);
                formBox.getChildren().addAll(birdBox);
                break;
            case CAT:
                Label exoticLabel = new Label("Är katten exotisk, så som lejon eller vildkatt?");
                exoticCheckBox = new CheckBox("Ja");
                exoticCheckBox.setSelected(((Cat)animal).isExotic());
                VBox catBox = new VBox(exoticLabel, exoticCheckBox);
                formBox.getChildren().addAll(catBox);
                break;
            case DOG:
                Label raceLabel = new Label("Hundras");
                raceField = new TextField();
                raceField.setText(((Dog)animal).getRace());
                VBox dogBox = new VBox(raceLabel, raceField);
                formBox.getChildren().addAll(dogBox);
                break;
            case HORSE: Label ponyLabel = new Label("Är hästen en ponny?");
                ponyCheckBox = new CheckBox("Ja");
                ponyCheckBox.setSelected(((Horse)animal).isPony());
                VBox horseBox = new VBox(ponyLabel, ponyCheckBox);
                formBox.getChildren().addAll(horseBox);
                break;
            default: throw new IllegalArgumentException("Är inte av typen Animal: " + animal.getAnimalType().getSwedish());
        }
        Button saveButton = new Button("Uppdatera");
        saveButton.setOnAction(e-> updateAnimal());

        formBox.getChildren().addAll(toListButton, saveButton);

        BorderPane root = new BorderPane();
        root.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        root.setRight(formBox);
        root.setPadding(new Insets(40));

        //TODO grafisk varning

        toListButton.setOnAction(e-> sceneManager.showRoot(GUIType.LIST_ANIMALS));
        return root;

    }

    private void updateAnimal() {
        try {
            if(!nameField.getText().trim().equals(animal.getName())){
            animal.setName(nameField.getText().trim());
            }
            if(!colorField.getText().trim().equals(animal.getColor())){
                animal.setColor(colorField.getText().trim());
            }
            if(!descriptionField.getText().trim().equals(animal.getDescription())){
                animal.setDescription(descriptionField.getText().trim());
            }
            if(!imageFileLocation.get().equals(animal.getImageFileLocation())){
                animal.setImageFileLocation(
                        ImageManager.saveFileToDirectory(imageFileLocation.get(),
                        "media" + File.separator + "animals")
                );
            }
            switch (animal.getAnimalType()){
                case BIRD:
                    if(flyingCheckBox.isSelected() != ((Bird)animal).isFlying()){
                        ((Bird)animal).setFlying(flyingCheckBox.isSelected());
                    }
                break;
                case CAT:
                    if(exoticCheckBox.isSelected() != ((Cat)animal).isExotic()){
                        ((Cat)animal).setExotic(exoticCheckBox.isSelected());
                    }
                break;

                case DOG:
                    if(!raceField.getText().trim().equals(((Dog)animal).getRace())){
                        ((Dog)animal).setRace(raceField.getText());
                    }
                    break;
                case HORSE:
                    if(ponyCheckBox.isSelected() != ((Horse)animal).isPony()){
                        ((Horse)animal).setPony(ponyCheckBox.isSelected());
                    }
                    break;
                default:
                    throw new IllegalArgumentException(animal.getAnimalType().getSwedish() + " är inte av typen Animal.");
            }

            animalService.updateAnimal(animal);
        } catch (AnimalNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    /**TODO Lägg till på alla fält
     * Sätter en lyssnare på ett textfält för att se ifall inskriven text är fungerande, om inte så visas en varningstext
     * @param field
     * @param warningLabel
     * @param warningMessage
     * @param validator
     */
    private void addValidationListener(TextField field, Label warningLabel,
                                       String warningMessage, Predicate<String> validator){
        field.focusedProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    if(!newValue && !field.getText().isEmpty()){
                        if (validator.test(field.getText())) {
                            warningLabel.setVisible(false);
                        } else {
                            warningLabel.setVisible(true);
                            warningLabel.setText(warningMessage);
                        }
                    }
                }));
    }

    private void cleanFields(Node... fields){
        for (Node field : fields){
            if(field instanceof TextInputControl){
                ((TextInputControl) field).clear();
            } else if (field instanceof ComboBox) {
                ((ComboBox<?>)field).setValue(null);
            } else if (field instanceof CheckBox) {
                ((CheckBox)field).setSelected(false);
            }
        }
    }
}
