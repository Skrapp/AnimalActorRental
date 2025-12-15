package gui;


import entity.animals.Animal;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AnimalListing {
    Animal animal;

    public AnimalListing(Animal animal) {
        this.animal = animal;
    }

    public HBox getListing(){
        HBox listingBox = new HBox(20);
        listingBox.setMinWidth(200);
        VBox imageBox = new VBox();
        ImageView image = null;

        if(animal.getImageFileLocation() != null) {
            try {
                image = new ImageView(new Image(new FileInputStream(animal.getImageFileLocation())));
                image.setFitWidth(250);
                image.setFitHeight(250);
                image.setPreserveRatio(true);
                imageBox.getChildren().add(image);
            } catch (FileNotFoundException e) {
                System.out.println("Filen hittades inte");
                imageBox.getChildren().add(new Label("Bild hittades inte"));
            }
        }else {
            imageBox.getChildren().add(new Label("Ingen bild"));
        }

        Label nameLabel = new Label(animal.getName());
        Label typeLabel = new Label(animal.getType().getSwedish().toUpperCase());
        Label availableLabel = new Label((animal.isAvailable() ? "Tillgänglig" : "Otillgänglig"));
        Text descriptionText = new Text(animal.getDescription() + "\n" + animal.specificAttributes());
        descriptionText.setWrappingWidth(listingBox.getMinWidth());
        Button rentButton = new Button("Hyr " + animal.getName());

        VBox descriptionBox =  new VBox(10, typeLabel, availableLabel, nameLabel, descriptionText, rentButton);
        listingBox.getChildren().addAll(imageBox, descriptionBox);
        listingBox.setPadding(new Insets(20));
        listingBox.setBorder(new Border(
                new BorderStroke(
                        Color.GRAY,
                        BorderStrokeStyle.SOLID,
                        new CornerRadii(10),
                        new BorderWidths(3)))
        );
        return listingBox;
    }
}
