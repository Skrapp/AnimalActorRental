package gui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import service.MemberService;

public class MainGUI {
    private Stage primaryStage;
    private MemberService memberService = new MemberService();

    public MainGUI(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void start(){
        primaryStage.setWidth(700);
        primaryStage.setHeight(500);
        primaryStage.setScene(new Scene(AddMember.start(memberService)));
    }
}
