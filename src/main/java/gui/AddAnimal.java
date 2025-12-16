package gui;

import entity.animals.*;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import service.AnimalService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AddAnimal {
    private final SceneManager sceneManager;
    private final AnimalService animalService;
    private final Desktop desktop = Desktop.getDesktop();

    public AddAnimal(SceneManager sceneManager, AnimalService animalService) {
        this.sceneManager = sceneManager;
        this.animalService = animalService;
    }

    public Parent start(){
        //Skapar och placerar nodes
        Label titleLabel = new Label("Lägg till nytt djur");
        Label typeLabel = new Label("Välj typ av djur");
        ComboBox<String> typeComboBox = new ComboBox<>(FXCollections.observableArrayList(
                        Arrays.stream(
                                AnimalType.values())
                                .map(AnimalType::getSwedish)
                                .collect(Collectors.toList()))
        );
        Label nameLabel = new Label("Namn");
        TextField nameField = new TextField();
        Label descriptionLabel = new Label("Beskrivning");
        TextArea descriptionField = new TextArea();
        descriptionField.setWrapText(true);
        descriptionField.setPrefWidth(500-40*2);
        Label colorLabel = new Label("Färg");
        TextField colorField = new TextField();

        Button toListButton = new Button("Se alla djur");

        //Lägg till bild
        Button addImageButton = new Button("Lägg till bild");
        Label choosenImageLabel = new Label("Ingen bild vald");
        AtomicReference<String> imageFileLocation = new AtomicReference<>("");

        addImageButton.setOnAction(e -> {
                    File file = ImageManager.chooseImage(addImageButton);
                    if (file != null) {
                        imageFileLocation.set(file.getAbsolutePath());
                        choosenImageLabel.setText(imageFileLocation.get());
                    }
        });

        VBox typeBox = new VBox(typeLabel, typeComboBox);
        VBox nameBox = new VBox(nameLabel, nameField);
        VBox ColorBox = new VBox(colorLabel, colorField);
        VBox addImageBox = new VBox(addImageButton, choosenImageLabel);
        VBox descriptionBox = new VBox(descriptionLabel, descriptionField);

        //Fågel
        Label flyingLabel = new Label("kan fågeln flyga?");
        CheckBox flyingCheckBox = new CheckBox("Ja");
        Button addBirdButton = new Button("Lägg till fågel");
        VBox birdBox = new VBox(flyingLabel, flyingCheckBox);

        //Katt
        Label exoticLabel = new Label("Är katten exotisk, så som lejon eller vildkatt?");
        CheckBox exoticCheckBox = new CheckBox("Ja");
        Button addCatButton = new Button("Lägg till katt");
        VBox catBox = new VBox(exoticLabel, exoticCheckBox);

        //Hund
        Label raceLabel = new Label("Hundras");
        TextField raceField = new TextField();
        Button addDogButton = new Button("Lägg till hund");
        VBox dogBox = new VBox(raceLabel, raceField);

        //Häst
        Label ponyLabel = new Label("Är hästen en ponny?");
        CheckBox ponyCheckBox = new CheckBox("Ja");
        Button addHorseButton = new Button("Lägg till häst");
        VBox horseBox = new VBox(ponyLabel, ponyCheckBox);

        VBox animalTypeBox = new VBox(10);

        VBox formBox = new VBox(10, typeBox, nameBox, ColorBox, addImageBox, descriptionBox, animalTypeBox, toListButton);
        formBox.setAlignment(Pos.CENTER_RIGHT);

        BorderPane root = new BorderPane();
        root.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        root.setRight(formBox);
        root.setPadding(new Insets(40));

        //Funktioner till nodes
        //TODO grafisk varning
        addBirdButton.setOnAction(e-> {
            try {
                animalService.addAnimal(new Bird(nameField.getText(), colorField.getText(), descriptionField.getText(),
                        ImageManager.saveFileToDirectory(imageFileLocation.get(), "media" + File.separator + "animals"),
                        flyingCheckBox.isSelected()));
                cleanFields(nameField, colorField, descriptionField, flyingCheckBox);
            } catch (NumberFormatException ex) {
                System.out.println("\"" + colorField.getText() + "\" är inte en giltig siffra.");
            } catch (IOException ex){
                System.out.println("Blev fel i filhantering.");
                System.out.println(ex);
            }
        });

        addCatButton.setOnAction(e-> {
            try {
                animalService.addAnimal(new Cat(nameField.getText(), colorField.getText(), descriptionField.getText(),
                        ImageManager.saveFileToDirectory(imageFileLocation.get(), "media" + File.separator + "animals"),
                        exoticCheckBox.isSelected()));
                cleanFields(nameField, colorField, descriptionField, exoticCheckBox);
            } catch (NumberFormatException ex) {
                System.out.println("\"" + colorField.getText() + "\" är inte en giltig siffra.");
            } catch (IOException ex){
                System.out.println("Blev fel i filhantering.");
                System.out.println(ex);
            }
        });

        addDogButton.setOnAction(e-> {
            try {
                animalService.addAnimal(new Dog(nameField.getText(), colorField.getText(), descriptionField.getText(),
                        ImageManager.saveFileToDirectory(imageFileLocation.get(), "media" + File.separator + "animals"),
                        raceField.getText()));
                cleanFields(nameField, colorField, descriptionField, raceField);
            } catch (NumberFormatException ex) {
                System.out.println("\"" + colorField.getText() + "\" är inte en giltig siffra.");
            } catch (IOException ex){
                System.out.println("Blev fel i filhantering.");
                System.out.println(ex);
            }
        });

        addHorseButton.setOnAction(e-> {
            try {
                animalService.addAnimal(new Horse(nameField.getText(), colorField.getText(), descriptionField.getText(),
                        ImageManager.saveFileToDirectory(imageFileLocation.get(), "media" + File.separator + "animals"),
                        ponyCheckBox.isSelected()));
                cleanFields(nameField, colorField, descriptionField, raceField);
            } catch (NumberFormatException ex) {
                System.out.println("\"" + colorField.getText() + "\" är inte en giltig siffra.");
            } catch (IOException ex){
                System.out.println("Blev fel i filhantering.");
                System.out.println(ex);
            }
        });

        typeComboBox.setOnAction(e -> {
            switch (typeComboBox.getValue().toLowerCase()){
                case "fågel" : animalTypeBox.getChildren().setAll(birdBox, addBirdButton);
                break;
                case "katt" : animalTypeBox.getChildren().setAll(catBox, addCatButton);
                break;
                case "hund" : animalTypeBox.getChildren().setAll(dogBox, addDogButton);
                break;
                case "häst" : animalTypeBox.getChildren().setAll(horseBox, addHorseButton);
                break;
                default: animalTypeBox.getChildren().clear();
            }
        });



        toListButton.setOnAction(e-> sceneManager.showRoot(GUIType.LIST_ANIMALS));

        //TODO om en level som behöver registering så som betalning eller studentkort ska en varning komma upp, och när
        // knapp trycks ska en popup dyka upp som man behöver hantera innan medlem skapas och läggs till.

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
}
