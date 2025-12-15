package gui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import service.AnimalService;
import service.MemberService;

public class MainGUI {
    private Stage primaryStage;
    private MemberService memberService = new MemberService();
    private AnimalService animalService = new AnimalService();

    public MainGUI(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void start(){
        primaryStage.setWidth(700);
        primaryStage.setHeight(500);

        new AddAnimal(primaryStage, animalService).start();
        //new AddMember(primaryStage, memberService).start();
        primaryStage.show();
    }
}
