package gui;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AnimalListing {
    private String ImageFileName;
    private String name;
    private String type;
    private boolean available;
    private String description;
    private String classSpecificAttribute;

    public AnimalListing(String imageFileName, String name, String type, boolean available, String description, String classSpecificAttribute) {
        ImageFileName = imageFileName;
        this.name = name;
        this.type = type;
        this.available = available;
        this.description = description;
        this.classSpecificAttribute = classSpecificAttribute;
    }

    public AnimalListing(String name, String type, boolean available, String description, String classSpecificAttribute) {
        this.name = name;
        this.type = type;
        this.available = available;
        this.description = description;
        this.classSpecificAttribute = classSpecificAttribute;
    }

    public HBox getListing(){
        HBox listingBox = new HBox(20);
        listingBox.setMinWidth(200);
        ImageView image = null;
        try {
            image = new ImageView(new Image(new FileInputStream("media/search.png")));
            image.setPreserveRatio(true);
            image.setFitHeight(250);
        } catch (FileNotFoundException e) {
            System.out.println("Filen hittades inte");
            throw new RuntimeException(e);
        }
        Label nameLabel = new Label(name);
        Label typeLabel = new Label(type.toUpperCase());
        Label availableLabel = new Label((available ? "Tillgänglig" : "Otillgänglig"));
        Text descriptionText = new Text(description + "\n" + classSpecificAttribute);
        descriptionText.setWrappingWidth(listingBox.getMinWidth());
        Button rentButton = new Button("Hyr " + name);

        VBox descriptionBox =  new VBox(10, typeLabel, availableLabel, nameLabel, descriptionText, rentButton);
        listingBox.getChildren().addAll(image, descriptionBox);
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
