package gui;

import entity.member.Member;
import entity.member.pricepolicy.PricePolicy;
import exceptions.PricePolicyNotFoundException;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import service.MemberService;

import java.io.IOException;
import java.util.function.Predicate;


public class AddMember {


    public static Parent start(MemberService memberService){
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

        VBox nameBox = new VBox(nameLabel, nameField);
        VBox productionsBox = new VBox(productionsLabel, productionsField);
        VBox levelBox = new VBox(levelLabel, levelComboBox);

        VBox formBox = new VBox(10, nameBox, levelBox, productionsBox, addMemberButton);
        addMemberButton.setAlignment(Pos.CENTER_RIGHT);
        formBox.setAlignment(Pos.CENTER_RIGHT);

        BorderPane root = new BorderPane();
        root.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        root.setRight(formBox);
        root.setPadding(new Insets(40));

        //Funktioner till nodes
        addMemberButton.setOnAction(e-> {
            try {
                memberService.addMember(nameField.getText(), levelComboBox.getValue(), Integer.parseInt(productionsField.getText()));
            } catch (NumberFormatException ex) {
                System.out.println("\"" + productionsField.getText() + "\" är inte en giltig siffra.");
            } catch (PricePolicyNotFoundException ex){
                System.out.println("\"" + levelComboBox.getValue() + "\" är inte en giltig level.");
            } catch (IOException ex){
                System.out.println("Blev fel i filhantering.");
            }
        });

        //TODO om en level som behöver registering så som betalning eller studentkort ska en varning komma upp, och när
        // knapp trycks ska en popup dyka upp som man behöver hantera innan medlem skapas och läggs till.

        return root;
    }

    private static void addMember(String name, String level, int productions) throws PricePolicyNotFoundException {
        PricePolicy pricePolicy = PricePolicy.getFromString(level);
        Member member = new Member(name, pricePolicy, productions);
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

    private static void cleanFields(){

    }
}
