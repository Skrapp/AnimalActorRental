package gui;

import entity.animals.Animal;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import service.AnimalService;
import service.RentalService;

import java.io.IOException;

public class ListAnimals {
    private SceneManager sceneManager;
    private AnimalService animalService;
    private RentalService rentalService;

    public ListAnimals(SceneManager sceneManager, AnimalService animalService, RentalService rentalService) {
        this.sceneManager = sceneManager;
        this.animalService = animalService;
        this.rentalService = rentalService;
    }

    public Parent start(){
        VBox listingBox = new VBox(20);
        listingBox.setPadding(new Insets(20));
        ScrollPane scrollPane = new ScrollPane(listingBox);
        try {
            for(Animal animal : animalService.getAllAnimals()){
                System.out.println(animal);

                AnimalListing animalListing = new AnimalListing(sceneManager, animal);
                listingBox.getChildren().add(animalListing.getListing());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        VBox root = new VBox(scrollPane);
        return root;
    }
}
