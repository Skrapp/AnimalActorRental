package gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.MemberService;

public class ListMembers {
    public static void start(Stage primaryStage, MemberService memberService){
        Label titelLabel = new Label("Medlemmar");
        Button addMemberButton = new Button("Lägg till ny medlem");
        TableView memberTable = new TableView<>();
        memberTable.setEditable(false);
        TableColumn nameColumn = new TableColumn<>("Namn");
        TableColumn idColumn = new TableColumn<>("ID");
        TableColumn levelColumn = new TableColumn<>("Level");
        TableColumn editButtonColumn= new TableColumn<>();
        memberTable.getColumns().addAll(idColumn, nameColumn, levelColumn, editButtonColumn);

        VBox root = new VBox(titelLabel, addMemberButton, memberTable);
        root.setPadding(new Insets(40));
        primaryStage.setScene(new Scene(root));

        //Funktioner till nodes
        addMemberButton.setOnAction(e-> AddMember.start(primaryStage, memberService));
    }
}
