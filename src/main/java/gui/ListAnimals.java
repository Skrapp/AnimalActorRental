package gui;

import entity.animals.Animal;
import entity.animals.AnimalType;
import exceptions.AnimalNotFoundException;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import service.AnimalService;
import service.RentalService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class ListAnimals {
    private SceneManager sceneManager;
    private AnimalService animalService;
    private RentalService rentalService;

    VBox listingBox;
    TextField searchField;

    public ListAnimals(SceneManager sceneManager, AnimalService animalService, RentalService rentalService) {
        this.sceneManager = sceneManager;
        this.animalService = animalService;
        this.rentalService = rentalService;
    }

    public Parent start(){
        listingBox = new VBox(20);
        listingBox.setPadding(new Insets(20));
        ScrollPane scrollPane = new ScrollPane(listingBox);

        //Sökfält
        searchField = new TextField();
        Button searchButton = new Button();
        HBox filterBox = new HBox(10, searchField, searchButton);
        try{
            ImageView searchImage = new ImageView(new Image(new FileInputStream("media/search.png")));
            searchImage.setPreserveRatio(true);
            searchImage.setFitHeight(15);
            searchButton.setGraphic(searchImage);
        } catch (FileNotFoundException e){
            System.out.println(e);
            searchButton.setText("Sök");
        }

        searchButton.setOnAction(e -> {
                    try {
                        fillList(
                                animalService.getFilteredAnimals(
                                        searchField.getText().trim(),
                                        Animal.class));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );

        //Fyller listan
        try {
            fillList(animalService.getAllAnimals());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        VBox root = new VBox(20, filterBox, scrollPane);
        root.setPadding(new Insets(40));
        return root;
    }

    private void fillList(List<Animal> animals) {
        listingBox.getChildren().clear();
        for(Animal animal : animals){
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
                } catch (AnimalNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            HBox buttonBox = new HBox(20, rentButton, editButton, removeButton);
            animalListing.getChildren().add(buttonBox);
            listingBox.getChildren().add(animalListing);
        }
    }
}
