package gui;

import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class ImageGetter {
    //Öppnar operativsystemets filsökare så användaren kan välja en bild
    public static File chooseImage(Node nodeAsking){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                //Filtrerar så att endast godkända filer visas
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));
        Window window = nodeAsking.getScene().getWindow();
        return fileChooser.showOpenDialog(window);
    }
}
