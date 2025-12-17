package gui;

import dao.Rental;
import entity.animals.Animal;
import entity.member.Member;
import exceptions.AnimalNotFoundException;
import exceptions.MemberNotFoundException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import service.RentalService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ListRentals {
    private SceneManager sceneManager;
    private RentalService rentalService;
    private Member member;

    private TableView<Rental> rentalsTable;

    public ListRentals(SceneManager sceneManager, RentalService rentalService, Member member) {
        this.sceneManager = sceneManager;
        this.rentalService = rentalService;
        this.member = member;
    }

    public Parent start(){
        Label titelLabel = new Label(member.getName() + "s hyrningar");

        rentalsTable = new TableView<>();
        rentalsTable.setEditable(true);
        TableColumn animalColumn = new TableColumn<>("Djur");
        TableColumn<Rental, String> animalTypeColumn = new TableColumn<>("Djurtyp");
        animalTypeColumn.setCellValueFactory(c
                -> new SimpleStringProperty(c.getValue().getAnimal().getAnimalType().getSwedish())
        );
        TableColumn<Rental, String> animalNameColumn = new TableColumn<>("Namn");
        animalNameColumn.setCellValueFactory(c
                -> c.getValue().getAnimal().nameProperty()
        );
        animalColumn.getColumns().setAll(animalTypeColumn, animalNameColumn);

        TableColumn<Rental, String> dateColumn = new TableColumn<>("Datum");
        dateColumn.setCellValueFactory(c
                -> new SimpleStringProperty(c.getValue().getDateFrom().toString() +
                " - " + c.getValue().getDateTo().toString())
        );

        TableColumn<Rental, String> priceColumn = new TableColumn<>("Pris");
        priceColumn.setCellValueFactory(c
                -> new SimpleStringProperty(c.getValue().getPrice() + ":-")
        );
        TableColumn<Rental, String> returnedColumn = new TableColumn<>("Återlämnad");
        returnedColumn.setCellValueFactory(c
                -> c.getValue().isReturned() ?
                    new SimpleStringProperty("Ja")
                    : new SimpleStringProperty("Nej"));

        rentalsTable.getColumns().setAll(animalColumn, dateColumn, priceColumn, returnedColumn);

        fillTable();

        Button listMembersButton = new Button("Se alla medlemmar");
        listMembersButton.setOnAction(e-> sceneManager.showRoot(GUIType.LIST_MEMBERS));

        Button returnRentalButton = new Button("Återlämna markerat djur");
        returnRentalButton.setOnAction(e -> {
            TableView.TableViewSelectionModel<Rental> selectionModel = rentalsTable.getSelectionModel();
            if (selectionModel.isEmpty()) {
                System.out.println("Ingen medlem vald.");
                return;
            }

            ObservableList<Integer> selectedRows = selectionModel.getSelectedIndices();
            List<Rental> rentalsToReturn = selectedRows.stream()
                    .map(row -> rentalsTable.getItems().get(row))
                    .collect(Collectors.toList());

            try {
                List<Animal> animalsReturned = rentalService.returnAnimals(member, rentalsToReturn);
                StringBuilder animalString = new StringBuilder();
                for (Animal animal : animalsReturned){
                    animalString.append(animal.getName() + ", ");
                }
                Alert returnedAlert = new Alert(Alert.AlertType.INFORMATION,
                        "Djur som har lämnats tillbaka:\n" + animalString);
                returnedAlert.setTitle("Återlämnade djur");
                returnedAlert.showAndWait();
                sceneManager.showRoot(GUIType.LIST_RENTALS, member);
            } catch (IOException ex) {
                new Alert(Alert.AlertType.ERROR, "Blev fel i filhantering. " + ex.getMessage()).showAndWait();
                throw new RuntimeException(ex);
            } catch (MemberNotFoundException ex) {
                new Alert(Alert.AlertType.ERROR, "Medlemmen finns inte. ID: " + member.getId() + ". " + ex.getMessage()).showAndWait();
                throw new RuntimeException(ex);
            } catch (AnimalNotFoundException ex) {
                new Alert(Alert.AlertType.ERROR, "Djur går inte att få tag på. " + ex.getMessage()).showAndWait();
                throw new RuntimeException(ex);
            }
        });

        VBox rentalsBox = new VBox(rentalsTable, returnRentalButton);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(40));
        root.setTop(titelLabel);
        root.setCenter(rentalsBox);
        return root;
    }

    private void fillTable() {
        ObservableList<Rental> rentals = FXCollections.observableArrayList(member.getRentalHistory());
        rentalsTable.setItems(rentals);
    }
}
