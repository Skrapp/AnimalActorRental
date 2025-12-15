package gui;

import entity.animals.*;
import javafx.collections.FXCollections;
import javafx.scene.layout.HBox;
import service.AnimalService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Predicate;

public class AddAnimal {
    private final Stage primaryStage;
    private final AnimalService animalService;

    public AddAnimal(Stage primaryStage, AnimalService animalService) {
        this.primaryStage = primaryStage;
        this.animalService = animalService;
    }

    public void start(){
        //Skapar och placerar nodes
        Label titleLabel = new Label("Lägg till nytt djur");
        Label typeLabel = new Label("Välj typ av djur");
        ComboBox<String> typeComboBox = new ComboBox<>(FXCollections.observableArrayList(Animal.getAllAnimalTypes()));
        Label nameLabel = new Label("Namn");
        TextField nameField = new TextField();
        Label descriptionLabel = new Label("beskrivning");
        TextArea descriptionField = new TextArea();
        descriptionField.setPrefWidth(220);
        Label colorLabel = new Label("Färg");
        TextField colorField = new TextField();
        Button toListButton = new Button("Se alla djur");

        VBox typeBox = new VBox(typeLabel, typeComboBox);
        VBox nameBox = new VBox(nameLabel, nameField);
        VBox ColorBox = new VBox(colorLabel, colorField);
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

        VBox formBox = new VBox(10, typeBox, nameBox, ColorBox, descriptionBox, animalTypeBox, toListButton);
        addHorseButton.setAlignment(Pos.CENTER_RIGHT);
        addDogButton.setAlignment(Pos.CENTER_RIGHT);
        addCatButton.setAlignment(Pos.CENTER_RIGHT);
        addBirdButton.setAlignment(Pos.CENTER_RIGHT);
        formBox.setAlignment(Pos.CENTER_RIGHT);

        BorderPane root = new BorderPane();
        root.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        root.setRight(formBox);
        root.setPadding(new Insets(40));

        primaryStage.setScene(new Scene(root));

        //Funktioner till nodes
        //TODO grafisk varning
        addBirdButton.setOnAction(e-> {
            try {
                animalService.addAnimal(new Bird(nameField.getText(), colorField.getText(), descriptionField.getText(),
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

        //toListButton.setOnAction(e-> new ListAnimals(primaryStage, memberService).start());

        //TODO om en level som behöver registering så som betalning eller studentkort ska en varning komma upp, och när
        // knapp trycks ska en popup dyka upp som man behöver hantera innan medlem skapas och läggs till.


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
