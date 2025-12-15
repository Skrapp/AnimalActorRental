package gui;

import entity.animals.Animal;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.AnimalService;
import service.RentalService;

import java.io.IOException;

public class ListAnimals {
    private Stage primaryStage;
    private AnimalService animalService;
    private RentalService rentalService;

    public ListAnimals(Stage primaryStage, AnimalService animalService, RentalService rentalService) {
        this.primaryStage = primaryStage;
        this.animalService = animalService;
        this.rentalService = rentalService;
    }

    public void start(){
        VBox listingBox = new VBox(20);
        listingBox.setPadding(new Insets(20));
        ScrollPane scrollPane = new ScrollPane(listingBox);
        try {
            for(Animal animal : animalService.getAllAnimals()){
                System.out.println(animal);

                AnimalListing animalListing = new AnimalListing(animal.getName(), animal.getType().getSwedish(),
                        true, animal.getDescription(), animal.specificAttributes());
                listingBox.getChildren().add(animalListing.getListing());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        VBox root = new VBox(scrollPane);
        primaryStage.setScene(new Scene(root));
    }
}
