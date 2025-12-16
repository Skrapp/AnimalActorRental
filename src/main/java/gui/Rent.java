package gui;

import entity.animals.Animal;
import entity.member.Member;
import exceptions.MemberNotFoundException;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import service.*;

import java.io.IOException;

public class Rent {
    private SceneManager sceneManager;
    private RentalService rentalService;
    private AnimalService animalService;
    private MemberService memberService;
    private Animal animalToRent;

    TextField memberIDField;
    HBox memberInfoBox;

    public Rent(SceneManager sceneManager,
                RentalService rentalService, AnimalService animalService, MemberService memberService,
                Animal animalToRent) {
        this.sceneManager = sceneManager;
        this.rentalService = rentalService;
        this.animalService = animalService;
        this.memberService = memberService;
        this.animalToRent = animalToRent;
    }

    public Parent start(){
        Label memberIDLabel = new Label("MedlemsID:");
        memberIDField = new TextField();
        Button findMemberButton = new Button("Hitta medlem");
        HBox searchMemberBox = new HBox(10, memberIDLabel, memberIDField, findMemberButton);
        Label noMember = new Label("Ingen medlem vald, skriv in medlemsID ovan");
        memberInfoBox = new HBox(10,noMember);
        HBox animalListing = new AnimalListing(animalToRent).getListing();

        findMemberButton.setOnAction(e -> displayMember());

        VBox root = new VBox(20, searchMemberBox, memberInfoBox, animalListing);
        root.setPadding(new Insets(40));
        return root;
    }

    private void displayMember() {
        try {
            Member member = memberService.getMemberByID(memberIDField.getText());
            memberInfoBox.getChildren().setAll(
                    new HBox(5, new Label("MedlemsID:"), new Label(member.getId())),
                    new HBox(5, new Label("Namn:"), new Label(member.getName()))
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (MemberNotFoundException e) {
            memberInfoBox.getChildren().setAll(new Label("Finns ingen medlem med valt medlemsID"));
        }
    }


}
