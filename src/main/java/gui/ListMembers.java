package gui;

import entity.member.Member;
import entity.member.pricepolicy.PricePolicy;
import exceptions.MemberNotFoundException;
import exceptions.PricePolicyNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import service.MemberService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListMembers {
    private final Stage primaryStage;
    private final MemberService memberService;

    public ListMembers(Stage primaryStage, MemberService memberService) {
        this.primaryStage = primaryStage;
        this.memberService = memberService;
    }

    public void start(){
        Label titelLabel = new Label("Medlemmar");
        Button addMemberButton = new Button("Lägg till ny medlem");

        //Sökfält
        TextField searchField = new TextField();
        Button searchButton = new Button();
        try{
            ImageView searchImage = new ImageView(new Image(new FileInputStream("media/search.png")));
            searchImage.setPreserveRatio(true);
            searchImage.setFitHeight(15);
            searchButton.setGraphic(searchImage);
        } catch (FileNotFoundException e){
            System.out.println(e);
            searchButton.setText("Sök");
        }

        //Tabell
        TableView<Member> memberTable = new TableView<>();
        memberTable.setEditable(true);
        TableColumn<Member, String> nameColumn = new TableColumn<>("Namn");
        nameColumn.setCellValueFactory(c -> c.getValue().nameProperty());
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(e -> {
            try {
                Member tableMember = e.getTableView().getItems().get(e.getTablePosition().getRow());
                Member member = memberService.getMemberByID(tableMember.getId());
                member.setName(e.getNewValue());
                memberService.updateMember(member);
                System.out.println(member.getId() + " uppdaterade namnet till " + member.getName() + ".");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (MemberNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        TableColumn<Member, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(c -> c.getValue().idProperty());
        TableColumn<Member, String> levelColumn = new TableColumn<>("Level");
        levelColumn.setCellValueFactory(c -> c.getValue().levelProperty().asString());
        levelColumn.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(PricePolicy.getAllLevels())));
        levelColumn.setOnEditCommit(e -> {
            try {
                Member tableMember = e.getTableView().getItems().get(e.getTablePosition().getRow());
                Member member = memberService.getMemberByID(tableMember.getId());
                member.setLevel(PricePolicy.getFromString(e.getNewValue()));
                memberService.updateMember(member);
                System.out.println(member.getId() + " uppdaterade level till " + member.getLevel() + ".");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (MemberNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (PricePolicyNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        TableColumn<Member, Number> productionsColumn = new TableColumn<>("Produktioner");
        productionsColumn.setCellValueFactory(c -> c.getValue().productionsProperty());
        productionsColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        productionsColumn.setOnEditCommit(e -> {
            try {
                Member tableMember = e.getTableView().getItems().get(e.getTablePosition().getRow());
                Member member = memberService.getMemberByID(tableMember.getId());
                member.setProductions(e.getNewValue().intValue());
                memberService.updateMember(member);
                System.out.println(member.getId() + " uppdaterade produktioner till " + member.getProductions() + ".");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (MemberNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        memberTable.getColumns().addAll(idColumn, nameColumn, levelColumn, productionsColumn);
        //Ta bort medlemmar
        Button removeMembersButton = new Button("Ta bort markerade medlemmar");
        try{
            ImageView trashImage = new ImageView(new Image(new FileInputStream("media/trash.png")));
            trashImage.setPreserveRatio(true);
            trashImage.setFitHeight(15);
            removeMembersButton.setGraphic(trashImage);
            removeMembersButton.setGraphicTextGap(5);
        } catch (FileNotFoundException e){
            System.out.println(e);
        }
        //TODO En knapp för att markera flera medlemmar

        //fyll tabell med medlemmar
        try {
            ObservableList<Member> members = FXCollections.observableArrayList(memberService.getAllMembers());
            memberTable.setItems(members);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        idColumn.setSortType(TableColumn.SortType.ASCENDING);
        sortTable(memberTable, idColumn);

        //Filtrering
        Button filterButton = new Button();
        try {
            ImageView filterImage = new ImageView(new Image(new FileInputStream("media/filter.png")));
            filterImage.setFitHeight(15);
            filterImage.setPreserveRatio(true);
            filterButton.setGraphic(filterImage);
        } catch (FileNotFoundException e) {
            System.out.println(e);
            filterButton.setText("Filter");
        }
        Label filterLabel = new Label("Filtrering");
        List<String> levels = PricePolicy.getAllLevels();
        Label productionsLabel = new Label("Antal produktioner");
        Label betweenLabel = new Label("―");
        //TODO gör så man endast kan skriva siffror. Om fälten lämnas tomma ska de inte räknas med
        TextField minProductionsField = new TextField("0");
        minProductionsField.setPrefWidth(45);
        TextField maxProductionsField = new TextField("1000");
        maxProductionsField.setPrefWidth(45);
        HBox numberOfProductionsBox = new HBox(10, minProductionsField, betweenLabel, maxProductionsField);
        Button confirmFilterButton = new Button("Filtrera");
        VBox levelsCheckBox = new VBox(10);
        for (String level : levels){
            CheckBox checkBox = new CheckBox(level);
            levelsCheckBox.getChildren().add(checkBox);
            checkBox.setSelected(true);
        }
        VBox filterBox = new VBox(10,filterLabel, levelsCheckBox, productionsLabel, numberOfProductionsBox,
                confirmFilterButton);
        StackPane overlay = new StackPane(filterBox);
        overlay.setVisible(false);

        //Sätt allt där det ska vara
        HBox searchBox = new HBox(5,searchField, searchButton);
        HBox filterButtonsBox = new HBox(20,filterButton, searchBox);
        filterButtonsBox.setAlignment(Pos.CENTER_RIGHT);
        HBox manageMemberBox = new HBox(20, removeMembersButton, addMemberButton);
        manageMemberBox.setAlignment(Pos.CENTER_RIGHT);
        VBox body = new VBox(20,titelLabel, filterButtonsBox, memberTable, manageMemberBox);
        HBox root = new HBox(body, overlay);
        root.setPadding(new Insets(40));
        primaryStage.setScene(new Scene(root));

        //Funktioner till nodes
        //Ta bort medlemmar
        removeMembersButton.setOnAction(e -> {
            TableView.TableViewSelectionModel<Member> selectionModel = memberTable.getSelectionModel();
            if(selectionModel.isEmpty()){
                System.out.println("Ingen medlem vald.");
                return;
            }

            ObservableList<Integer> selectedRows = selectionModel.getSelectedIndices();
            List<Member> membersToRemove = selectedRows.stream()
                    .map(row -> memberTable.getItems().get(row))
                    .collect(Collectors.toList());

            try {
                memberService.removeMembers(membersToRemove);

                memberTable.setItems(searchAndFilter(searchField.getText(), getSelectedLevels(levelsCheckBox.getChildren()),
                        Integer.parseInt(minProductionsField.getText()), Integer.parseInt(maxProductionsField.getText())));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (MemberNotFoundException ex) {
                throw new RuntimeException(ex);
            }

        });
        //Sökning och filtrering
        searchButton.setOnAction(e-> {
            try {
                memberTable.setItems(searchAndFilter(
                        searchField.getText(),getSelectedLevels(levelsCheckBox.getChildren()),
                        Integer.parseInt(minProductionsField.getText()),
                        Integer.parseInt(maxProductionsField.getText())));
                sortTable(memberTable, idColumn);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        filterButton.setOnAction(e->overlay.setVisible(true));

        confirmFilterButton.setOnAction(e-> {
            try {
                memberTable.setItems(searchAndFilter(
                        searchField.getText(), getSelectedLevels(levelsCheckBox.getChildren()),
                            Integer.parseInt(minProductionsField.getText()), Integer.parseInt(maxProductionsField.getText())));
                sortTable(memberTable, idColumn);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        addMemberButton.setOnAction(e-> new AddMember(primaryStage, memberService).start());
    }

    private List<Class<?extends PricePolicy>> getSelectedLevels(List<Node> nodes) {
        List<Class<? extends PricePolicy>> pricePolicyClasses = new ArrayList<>();
        for(Node node : nodes){
            if(node instanceof CheckBox && ((CheckBox) node).isSelected()){
                try {
                    pricePolicyClasses.add(PricePolicy.getFromString(((CheckBox) node).getText()).getClass());
                } catch (PricePolicyNotFoundException ex) {
                    System.out.println(ex);
                    System.out.println(((CheckBox) node).getText() + " är inte en giltig PricePolicy.");
                }
            }
        }
        System.out.println("PricePolicies att inkludera:");
        pricePolicyClasses.forEach(System.out::println);
        System.out.println();
        return pricePolicyClasses;
    }

    private void sortTable(TableView<Member> table, TableColumn<Member, String> sortColumn) {
        table.getSortOrder().add(sortColumn);
        table.sort();
    }

    private ObservableList<Member> searchAndFilter(String searchWord, List<Class<? extends PricePolicy>> pricePolicyClasses,
                                                   int minProductions, int maxProductions) throws IOException {
        return FXCollections.observableArrayList(memberService.getFilteredMembers(searchWord.trim(), pricePolicyClasses, minProductions, maxProductions)
        );
    }
}
