package gui;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class AddMember {


    public static Parent start(){
        BorderPane root = new BorderPane();
        Label titleLabel = new Label("Lägg till ny medlem");
        Label nameLabel = new Label("Namn");
        TextField nameField = new TextField();
        Label productionsLabel = new Label("Antal produktioner hen deltagit i");
        TextField productionsField = new TextField();
        Label levelLabel = new Label("Välj level");
        ComboBox<String> levelComboBox = new ComboBox<>();
        String[] levels = {
                "Golden",
                "Regular",
                "Student"
        };
        levelComboBox.setItems(FXCollections.observableArrayList(levels));
        Button addMemberButton = new Button("Lägg till medlem");

        VBox nameBox = new VBox(nameLabel, nameField);
        VBox productionsBox = new VBox(productionsLabel, productionsField);
        VBox levelBox = new VBox(levelLabel, levelComboBox);

        VBox formBox = new VBox(10, nameBox, levelBox, productionsBox, addMemberButton);
        addMemberButton.setAlignment(Pos.CENTER_RIGHT);

        root.setTop(titleLabel);
        root.setRight(formBox);



        return root;
    }
}
