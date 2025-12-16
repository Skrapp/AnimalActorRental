package gui;

import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;

public class ImageManager {
    //Öppnar operativsystemets filsökare så användaren kan välja en bild
    public static File chooseImage(Node nodeAsking){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                //Filtrerar så att endast godkända filer visas
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));
        Window window = nodeAsking.getScene().getWindow();
        return fileChooser.showOpenDialog(window);
    }

    //TODO Om det redan finns en fil, lägg på en siffra
    //TODO om ingen fil väljs ska
    public static String saveFileToDirectory(String absoluteFileLocation, String targetDirectory) throws IOException {
        if(absoluteFileLocation == null || absoluteFileLocation.isEmpty()){
            return null;
        }
        File absoluteFile = new File(absoluteFileLocation);
        File directory = new File(targetDirectory);
        if (!directory.isDirectory()){
            System.out.println("fel");
            throw new IllegalArgumentException(targetDirectory + " är inte en mapp");
        }
        File newFile = new File(targetDirectory + File.separator + absoluteFile.getName());
        Files.copy(absoluteFile.toPath(), newFile.toPath());
        return newFile.getPath();
    }
}
