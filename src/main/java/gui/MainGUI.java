package gui;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGUI {
    private Stage primaryStage;

    public MainGUI(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void start(){
        primaryStage.setScene(new Scene(AddMember.start()));
    }
}
