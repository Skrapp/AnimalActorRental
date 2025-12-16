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

    private TextField memberIDField;
    private HBox memberInfoBox;
    private Member memberToRent;

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
        Button listMembersButton = new Button("Se alla medlemmar");
        HBox searchMemberBox = new HBox(10, memberIDLabel, memberIDField, findMemberButton, listMembersButton);
        Label noMemberLabel = new Label("Ingen medlem vald, skriv in medlemsID ovan");
        memberInfoBox = new HBox(10,noMemberLabel);
        HBox animalListing = new AnimalListing(animalToRent).getListing();
        Label totalPrice = new Label(Double.toString(animalToRent.getPrice()));


        findMemberButton.setOnAction(e -> {
            Member member = getMemberFromField();
            if(member != null) {
                displayMember(member);
                totalPrice.setText(Double.toString(updatePrice()));
            }
        });

        VBox root = new VBox(20, searchMemberBox, memberInfoBox, animalListing, totalPrice);
        root.setPadding(new Insets(40));
        return root;
    }

    private double updatePrice() {
        return (memberToRent == null ? animalToRent.getPrice() : memberToRent.getLevel().applyDiscount(animalToRent.getPrice()));
    }

    private Member getMemberFromField() {
        try{
            memberToRent = memberService.getMemberByID(memberIDField.getText());
            return memberToRent;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (MemberNotFoundException e) {
            memberInfoBox.getChildren().setAll(new Label("Finns ingen medlem med valt medlemsID"));
            return null;
        }
    }

    private void displayMember(Member member) {
        memberInfoBox.getChildren().setAll(
            new HBox(5, new Label("MedlemsID:"), new Label(member.getId())),
            new HBox(5, new Label("Namn:"), new Label(member.getName())),
            new HBox(5, new Label("Level:"), new Label(member.getLevel().toString()))
        );

    }


}
