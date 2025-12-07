package gui;

import entity.member.Member;
import entity.member.pricepolicy.PricePolicy;
import exceptions.PricePolicyNotFoundException;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.MemberService;

import java.io.IOException;
import java.util.function.Predicate;


public class AddMember {


    public static void start(Stage primaryStage, MemberService memberService){
        //Skapar och placerar nodes
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
                "Student",
                "g"
        };
        levelComboBox.setItems(FXCollections.observableArrayList(levels));
        Button addMemberButton = new Button("Lägg till medlem");
        Button toListButton = new Button("Se alla medlemmar");

        VBox nameBox = new VBox(nameLabel, nameField);
        VBox productionsBox = new VBox(productionsLabel, productionsField);
        VBox levelBox = new VBox(levelLabel, levelComboBox);

        VBox formBox = new VBox(10, nameBox, levelBox, productionsBox, addMemberButton, toListButton);
        addMemberButton.setAlignment(Pos.CENTER_RIGHT);
        formBox.setAlignment(Pos.CENTER_RIGHT);

        BorderPane root = new BorderPane();
        root.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        root.setRight(formBox);
        root.setPadding(new Insets(40));

        primaryStage.setScene(new Scene(root));

        //Funktioner till nodes
        //TODO grafisk varning
        addMemberButton.setOnAction(e-> {
            try {
                if(memberService.addMember(nameField.getText(), levelComboBox.getValue(), Integer.parseInt(productionsField.getText()))){
                    cleanFields(nameField, productionsField,levelComboBox);
                }
            } catch (NumberFormatException ex) {
                System.out.println("\"" + productionsField.getText() + "\" är inte en giltig siffra.");
            } catch (PricePolicyNotFoundException ex){
                System.out.println("\"" + levelComboBox.getValue() + "\" är inte en giltig level.");
            } catch (IOException ex){
                System.out.println("Blev fel i filhantering.");
                System.out.println(ex);
            }
        });
        toListButton.setOnAction(e-> ListMembers.start(primaryStage, memberService));

        //TODO om en level som behöver registering så som betalning eller studentkort ska en varning komma upp, och när
        // knapp trycks ska en popup dyka upp som man behöver hantera innan medlem skapas och läggs till.


    }

    /**TODO Lägg till på alla fält
     * Sätter en lyssnare på ett textfält för att se ifall inskriven text är fungerande, om inte så visas en varningstext
     * @param field
     * @param warningLabel
     * @param warningMessage
     * @param validator
     */
    private static void addValidationListener(TextField field, Label warningLabel, String warningMessage, Predicate<String> validator){
        field.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if(!newValue && !field.getText().isEmpty()){
                if (validator.test(field.getText())) {
                    warningLabel.setVisible(false);
                } else {
                    warningLabel.setVisible(true);
                    warningLabel.setText(warningMessage);
                }
            }
        }));
    }

    private static void cleanFields(Node... fields){
        for (Node field : fields){
            if(field instanceof TextInputControl){
                ((TextInputControl) field).clear();
            } else if (field instanceof ComboBox) {
                ((ComboBox<?>)field).setValue(null);
            }
        }
    }
}
