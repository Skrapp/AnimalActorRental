package gui;

import entity.animals.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.AnimalService;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class EditAnimal {
    private final SceneManager sceneManager;
    private final AnimalService animalService;
    private final Animal animal;
    private final Desktop desktop = Desktop.getDesktop();

    public EditAnimal(SceneManager sceneManager, AnimalService animalService, Animal animal) {
        this.sceneManager = sceneManager;
        this.animalService = animalService;
        this.animal = animal;
    }

    public Parent start(){
        //Skapar och placerar nodes
        Label titleLabel = new Label("Lägg till nytt djur");
        Label nameLabel = new Label("Namn");
        TextField nameField = new TextField(animal.getName());
        Label descriptionLabel = new Label("Beskrivning");
        TextArea descriptionField = new TextArea(animal.getDescription());
        descriptionField.setWrapText(true);
        descriptionField.setPrefWidth(220);
        Label colorLabel = new Label("Färg");
        TextField colorField = new TextField(animal.getColor());

        Button toListButton = new Button("Se alla djur");

        //Lägg till bild, öppnar utforskaren (för Windows)
        Button addImageButton = new Button("Lägg till bild");
        Label choosenImageLabel = new Label(animal.getImageFileLocation() == null ? "Ingen bild vald" : animal.getImageFileLocation());
        AtomicReference<String> imageFileLocation = new AtomicReference<>("");


        addImageButton.setOnAction(e -> {
            File file = ImageGetter.chooseImage(addImageButton);
            if (file != null) {
                imageFileLocation.set(file.getAbsolutePath());
                choosenImageLabel.setText(imageFileLocation.get());
            }
        });

        VBox nameBox = new VBox(nameLabel, nameField);
        VBox colorBox = new VBox(colorLabel, colorField);
        VBox imageBox = new VBox(addImageButton, choosenImageLabel);
        VBox descriptionBox = new VBox(descriptionLabel, descriptionField);

        //Fågel
        Label flyingLabel = new Label("kan fågeln flyga?");
        CheckBox flyingCheckBox = new CheckBox("Ja");
        Button saveBirdButton = new Button("Spara fågel");
        VBox birdBox = new VBox(flyingLabel, flyingCheckBox);

        //Katt
        Label exoticLabel = new Label("Är katten exotisk, så som lejon eller vildkatt?");
        CheckBox exoticCheckBox = new CheckBox("Ja");
        Button saveCatButton = new Button("Spara katt");
        VBox catBox = new VBox(exoticLabel, exoticCheckBox);

        //Hund
        Label raceLabel = new Label("Hundras");
        TextField raceField = new TextField();
        Button saveDogButton = new Button("Spara hund");
        VBox dogBox = new VBox(raceLabel, raceField);

        //Häst
        Label ponyLabel = new Label("Är hästen en ponny?");
        CheckBox ponyCheckBox = new CheckBox("Ja");
        Button saveHorseButton = new Button("Spara häst");
        VBox horseBox = new VBox(ponyLabel, ponyCheckBox);

        VBox animalTypeBox = new VBox(10);

        VBox formBox = new VBox(10, nameBox, colorBox, imageBox, descriptionBox, animalTypeBox, toListButton);
        formBox.setAlignment(Pos.CENTER_RIGHT);

        BorderPane root = new BorderPane();
        root.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        root.setRight(formBox);
        root.setPadding(new Insets(40));

        //Funktioner till nodes
        //TODO grafisk varning
        saveBirdButton.setOnAction(e-> {
            try {
                animalService.addAnimal(new Bird(nameField.getText(), colorField.getText(), descriptionField.getText(),
                        saveFileToDirectory(imageFileLocation.get(), "media" + File.separator + "animals"),
                        flyingCheckBox.isSelected()));
                cleanFields(nameField, colorField, descriptionField, flyingCheckBox);
            } catch (NumberFormatException ex) {
                System.out.println("\"" + colorField.getText() + "\" är inte en giltig siffra.");
            } catch (IOException ex){
                System.out.println("Blev fel i filhantering.");
                System.out.println(ex);
            }
        });

        saveCatButton.setOnAction(e-> {
            try {
                animalService.addAnimal(new Cat(nameField.getText(), colorField.getText(), descriptionField.getText(),
                        exoticCheckBox.isSelected()));
                cleanFields(nameField, colorField, descriptionField, exoticCheckBox);
            } catch (NumberFormatException ex) {
                System.out.println("\"" + colorField.getText() + "\" är inte en giltig siffra.");
            } catch (IOException ex){
                System.out.println("Blev fel i filhantering.");
                System.out.println(ex);
            }
        });

        saveDogButton.setOnAction(e-> {
            try {
                animalService.addAnimal(new Dog(nameField.getText(), colorField.getText(), descriptionField.getText(),
                        raceField.getText()));
                cleanFields(nameField, colorField, descriptionField, raceField);
            } catch (NumberFormatException ex) {
                System.out.println("\"" + colorField.getText() + "\" är inte en giltig siffra.");
            } catch (IOException ex){
                System.out.println("Blev fel i filhantering.");
                System.out.println(ex);
            }
        });

        saveHorseButton.setOnAction(e-> {
            try {
                animalService.addAnimal(new Horse(nameField.getText(), colorField.getText(), descriptionField.getText(),
                        ponyCheckBox.isSelected()));
                cleanFields(nameField, colorField, descriptionField, raceField);
            } catch (NumberFormatException ex) {
                System.out.println("\"" + colorField.getText() + "\" är inte en giltig siffra.");
            } catch (IOException ex){
                System.out.println("Blev fel i filhantering.");
                System.out.println(ex);
            }
        });

        /*typeComboBox.setOnAction(e -> {
            switch (typeComboBox.getValue().toLowerCase()){
                case "fågel" : animalTypeBox.getChildren().setAll(birdBox, saveBirdButton);
                    break;
                case "katt" : animalTypeBox.getChildren().setAll(catBox, saveCatButton);
                    break;
                case "hund" : animalTypeBox.getChildren().setAll(dogBox, saveDogButton);
                    break;
                case "häst" : animalTypeBox.getChildren().setAll(horseBox, saveHorseButton);
                    break;
                default: animalTypeBox.getChildren().clear();
            }
        });*/



        //toListButton.setOnAction(e-> new ListAnimals(primaryStage, animalService, new RentalService()).start());
        return root;

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

    //TODO flytta till annan klass
    private String saveFileToDirectory(String absoluteFileLocation, String targetDirectory) throws IOException {
        File absoluteFile = new File(absoluteFileLocation);
        File directory = new File(targetDirectory);
        if (!directory.isDirectory()){
            System.out.println("fel");
            throw new FileAlreadyExistsException(targetDirectory + " är inte en mapp");
        }
        File newFile = new File(targetDirectory + File.separator + absoluteFile.getName());
        Files.copy(absoluteFile.toPath(), newFile.toPath());
        return newFile.getPath();
    }
}
