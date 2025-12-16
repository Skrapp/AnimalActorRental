package gui;


import entity.animals.Animal;
import javafx.geometry.Insets;
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
        listingBox.setMinWidth(250);
        ImageView image = null;


        //Sätter en bild, om bild inte finns i mappen eller det inte är någon bild kopplad till Animal används en placeholder
        try {
            image = new ImageView(new Image(new FileInputStream(animal.getImageFileLocation())));
            image.setFitWidth(200);
            image.setFitHeight(200);
            image.setPreserveRatio(true);
            listingBox.getChildren().add(image);
        } catch (FileNotFoundException | NullPointerException e) {
            System.out.println("Bild hittas inte");
            //Finns inte placeholder bilden så används en text istället
            try {
                image = new ImageView(new Image(new FileInputStream("media" + File.separator + "image not found.jpg")));
                image.setFitWidth(200);
                image.setFitHeight(200);
                image.setPreserveRatio(true);
                listingBox.getChildren().add(image);
            } catch (FileNotFoundException ex) {
                listingBox.getChildren().add(new Label("Bild hittades inte"));
            }
        }

        Label nameLabel = new Label(animal.getName());
        Label typeLabel = new Label(animal.getAnimalType().getSwedish().toUpperCase());
        Label availableLabel = new Label((animal.isAvailable() ? "Tillgänglig" : "Otillgänglig"));
        Text descriptionText = new Text(animal.getDescription() + "\n" + animal.specificAttributes());
        descriptionText.setWrappingWidth(listingBox.getMinWidth());

        VBox descriptionBox =  new VBox(10, typeLabel, availableLabel, nameLabel, descriptionText);
        listingBox.getChildren().addAll(descriptionBox);
        listingBox.setPadding(new Insets(20));
        listingBox.setBorder(new Border(new BorderStroke(
                        Color.GRAY,
                        BorderStrokeStyle.SOLID,
                        new CornerRadii(10),
                        new BorderWidths(3)))
        );

        return listingBox;
    }
}
