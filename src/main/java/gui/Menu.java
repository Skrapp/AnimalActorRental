package gui;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Menu {
    private SceneManager sceneManager;

    public Menu(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public Parent start(){
        GridPane menuPane = new GridPane();
        menuPane.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, null, null)));
        menuPane.setPadding(new Insets(40));

        Label animalLabel = new Label("Djurhantering");
        Hyperlink addAnimalLink = new Hyperlink("Lägg till nytt djur");
        Hyperlink listAnimalsLink = new Hyperlink("Hantera alla djur/Hyr djur");
        VBox animalLinks = new VBox(10, addAnimalLink, listAnimalsLink);

        Label memberLabel = new Label("Medlemshantering");
        Hyperlink addMemberLink = new Hyperlink("Lägg till ny medlem");
        Hyperlink listMembersLink = new Hyperlink("Hantera alla medlemmar");
        VBox memberLinks = new VBox(10, addMemberLink, listMembersLink);

        menuPane.add(memberLabel, 0,0);
        menuPane.add(memberLinks, 0, 1);
        menuPane.add(animalLabel, 1,0);
        menuPane.add(animalLinks, 1,1);

        addMemberLink.setTextFill(Color.WHITE);
        addAnimalLink.setTextFill(Color.WHITE);
        listMembersLink.setTextFill(Color.WHITE);
        listAnimalsLink.setTextFill(Color.WHITE);

        addAnimalLink.setOnAction(e->sceneManager.showRoot(GUIType.ADD_ANIMAL));
        listAnimalsLink.setOnAction(e->sceneManager.showRoot(GUIType.LIST_ANIMALS));
        addMemberLink.setOnAction(e->sceneManager.showRoot(GUIType.ADD_MEMBER));
        listMembersLink.setOnAction(e->sceneManager.showRoot(GUIType.LIST_MEMBERS));

        return menuPane;
    }
}
