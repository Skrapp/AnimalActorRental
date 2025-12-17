package gui;

import entity.animals.Animal;
import entity.member.Member;
import exceptions.AnimalNotFoundException;
import exceptions.MemberNotFoundException;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import service.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Rent {
    private SceneManager sceneManager;
    private RentalService rentalService;
    private AnimalService animalService;
    private MemberService memberService;
    private Animal animalToRent;

    private TextField memberIDField;
    private HBox memberInfoBox;
    private Member memberToRent;
    private DatePicker datePickerFrom;
    private DatePicker datePickerTo;
    private Label totalPrice;
    private Label totalDays;

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
        //Hitta medlem
        Label memberIDLabel = new Label("MedlemsID:");
        memberIDField = new TextField();
        Button findMemberButton = new Button("Hitta medlem");
        Button listMembersButton = new Button("Se alla medlemmar");
        HBox searchMemberBox = new HBox(10, memberIDLabel, memberIDField, findMemberButton, listMembersButton);

        findMemberButton.setOnAction(e -> updateMember());

        listMembersButton.setOnAction(e -> sceneManager.showRoot(GUIType.LIST_MEMBERS));

        Label noMemberLabel = new Label("Ingen medlem vald, skriv in medlemsID ovan");
        memberInfoBox = new HBox(10,noMemberLabel);

        //Djuret som ska hyras
        HBox animalListing = new AnimalListing(animalToRent).getListing();

        //Prisinfo
        totalPrice = new Label(Double.toString(animalToRent.getPrice()));
        totalDays = new Label("1 dag/-ar");
        HBox priceBox = new HBox(10, totalDays, totalPrice);

        //Välj datum
        datePickerFrom = new DatePicker(LocalDate.now());
        datePickerTo = new DatePicker();
        Label dateFromLabel = new Label("Välj datum att hyra från");
        Label dateToLabel = new Label("Väj datum att hyra till");
        VBox dateFromBox = new VBox(dateFromLabel, datePickerFrom);
        VBox dateToBox = new VBox(dateToLabel, datePickerTo);
        HBox dateBox = new HBox(dateFromBox, dateToBox);

        //TODO disable dagar som djuret redan på bokad på annat
        //Disable dagarna innan idag och efter hyra-fram-till-datum
        datePickerFrom.setDayCellFactory(d -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty
                        || date.isBefore(LocalDate.now())
                        || (datePickerTo.getValue() != null && date.isAfter(datePickerTo.getValue()))
                );
            }
        });
        datePickerFrom.setOnAction(e->{
            LocalDate date = datePickerFrom.getValue();
            System.err.println("Selected date: " + date);
            updateDays();
            updatePrice();
        });

        datePickerTo.setDayCellFactory(d -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(datePickerFrom.getValue().plusDays(1)));
            }
        });
        datePickerTo.setOnAction(e->{
            LocalDate date = datePickerFrom.getValue();
            updateDays();
            updatePrice();
            System.err.println("Selected date: " + date);
        });

        //Välj att hyra eller att avbryta
        Button rentButton = new Button("Hyr " + animalToRent.getName());
        Button cancelButton = new Button("Avbryt");
        HBox decisionBox = new HBox(20, rentButton, cancelButton);

        cancelButton.setOnAction(e->sceneManager.showRoot(GUIType.LIST_ANIMALS));

        //gör diable om inte allt är ifyllt
        rentButton.setOnAction(e->rent());

        VBox root = new VBox(20, searchMemberBox, memberInfoBox, animalListing, priceBox, dateBox, decisionBox);
        root.setPadding(new Insets(40));
        return root;
    }

    private void rent() {
        if(memberToRent == null){
            new Alert(Alert.AlertType.INFORMATION, "Ingen medlem vald").showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Ta emot betalning på " + getPrice() + " av hyra av " + animalToRent.getName() + "."
        );
        alert.setTitle("Betalning");
        alert.setHeaderText("Betalning");
        //Om användaren stänger fönstret med x knappen så stoppar ifPresent så kodblocket inte körs
        alert.showAndWait().ifPresent(buttonPressed -> {
            if (buttonPressed == ButtonType.OK) {
                try {
                    rentalService.rentAnimal(
                            memberToRent, animalToRent,
                            datePickerFrom.getValue(), datePickerTo.getValue(),
                            getPrice()
                    );
                    rentalService.receivePayment(getPrice());
                    System.out.println(memberToRent);
                    sceneManager.showRoot(GUIType.LIST_ANIMALS);
                } catch (IOException e) {
                    new Alert(Alert.AlertType.ERROR, "Blev fel i filhantering. " + e.getMessage()).showAndWait();
                    throw new RuntimeException(e);
                } catch (MemberNotFoundException e) {
                    new Alert(Alert.AlertType.ERROR, "Medlemmen finns inte. ID: " + memberToRent.getId() + ". " + e.getMessage()).showAndWait();
                    throw new RuntimeException(e);
                } catch (AnimalNotFoundException e){
                    new Alert(Alert.AlertType.ERROR, "Djur finns inte. ID: " + animalToRent.getId() + ". " + e.getMessage()).showAndWait();
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void updateMember() {
        memberToRent = getMemberFromField();
        if(memberToRent != null) {
            displayMember(memberToRent);
            updatePrice();
        }
    }

    private void updateDays() {
        long days = getDays();
        totalDays.setText(days + " dag/-ar");
    }

    private long getDays() {
        return datePickerTo.getValue() == null ?
                1 : ChronoUnit.DAYS.between(datePickerFrom.getValue(), datePickerTo.getValue());
    }

    private void updatePrice() {
        double updatedPrice = getPrice();
        totalPrice.setText(Double.toString(updatedPrice));
    }

    private double getPrice() {
        return (memberToRent == null ?
                animalToRent.getPrice() : memberToRent.getLevel().applyDiscount(animalToRent.getPrice())
        ) * getDays();
    }

    private Member getMemberFromField() {
        try{
            memberToRent = memberService.getMemberByID(memberIDField.getText());
            return memberToRent;
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Blev fel i filhantering. " + e.getMessage()).showAndWait();
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
