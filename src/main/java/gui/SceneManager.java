package gui;

import entity.animals.Animal;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import service.AnimalService;
import service.MemberService;
import service.RentalService;

public class SceneManager {
    private Stage primaryStage;
    private Parent menu;
    private MemberService memberService;
    private AnimalService animalService;
    private RentalService rentalService;

    public SceneManager(Stage primaryStage, MemberService memberService, AnimalService animalService, RentalService rentalService) {
        this.primaryStage = primaryStage;
        this.menu = new Menu(this).start();
        this.memberService = memberService;
        this.animalService = animalService;
        this.rentalService = rentalService;
    }

    public void showRoot(GUIType guiType){
        Parent root;
        switch (guiType){
            case ADD_ANIMAL: root = new AddAnimal(this, animalService).start();
                break;
            case ADD_MEMBER: root = new AddMember(this, memberService).start();
                break;
            case LIST_ANIMALS: root = new ListAnimals(this, animalService,rentalService).start();
            break;
            case LIST_MEMBERS: root = new ListMembers(this, memberService).start();
                break;
            default: throw new NullPointerException("Denna GUIType finns inte: " + guiType.name());
        }
        setRootAndMenu(root);
    }

    public void showRoot(GUIType guiType, Object requiredData){
        Parent root;
        switch (guiType){
            case EDIT_ANIMAL:
                if(requiredData instanceof Animal){
                    root = new EditAnimal(this, animalService, (Animal) requiredData).start();
                }else {
                    throw new IllegalArgumentException("Data är inte av typen Animal: " + requiredData);
                }
            break;
            case RENT:
                if(requiredData instanceof Animal){
                    root = new Rent(this,
                            rentalService, animalService, memberService,
                            (Animal) requiredData).start();
                }else {
                    throw new IllegalArgumentException("Data är inte av typen Animal: " + requiredData);
                }
            break;
            default: throw new NullPointerException("Denna GUIType finns inte: " + guiType.name());
        }
        setRootAndMenu(root);
    }

    private void setRootAndMenu(Parent root) {
        BorderPane rootAndMenu = new BorderPane();
        rootAndMenu.setCenter(root);
        rootAndMenu.setBottom(menu);
        primaryStage.setScene(new Scene(rootAndMenu));
    }
}
