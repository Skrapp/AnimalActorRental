package gui;

import entity.animals.Animal;
import exceptions.AnimalNotFoundException;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
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
                HBox animalListing =  new AnimalListing(animal).getListing();
                Button rentButton = new Button("Hyr " + animal.getName());
                Button editButton = new Button("Redigera");
                Button removeButton = new Button("Ta bort " + animal.getName());

                editButton.setOnAction(e -> sceneManager.showRoot(GUIType.EDIT_ANIMAL, animal));

                removeButton.setOnAction(e -> {
                    try {
                        animalService.removeAnimal(animal);
                        animalListing.getChildren().clear();
                        animalListing.getChildren().addAll(new Label(animal.getName() + " är borttagen"));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (AnimalNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                HBox buttonBox = new HBox(20, rentButton, editButton, removeButton);
                animalListing.getChildren().add(buttonBox);
                listingBox.getChildren().add(animalListing);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        VBox root = new VBox(scrollPane);
        return root;
    }
}
