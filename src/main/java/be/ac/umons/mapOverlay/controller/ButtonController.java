package be.ac.umons.mapOverlay.controller;

import be.ac.umons.mapOverlay.model.IntersectionsFinder;
import be.ac.umons.mapOverlay.model.map.Map;
import be.ac.umons.mapOverlay.model.map.MapInputStream;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;

public class ButtonController implements EventHandler<ActionEvent> {

    private final Stage primaryStage;
    private final IntersectionsFinder intersectionsFinder;
    private final FileChooser fileChooser = new FileChooser();

    public ButtonController(Stage primaryStage, IntersectionsFinder intersectionsFinder){
        this.primaryStage = primaryStage;
        this.intersectionsFinder = intersectionsFinder;
        fileChooser.setInitialDirectory(new File("cartes"));
    }

    @Override
    public void handle(ActionEvent event) {
        switch (((Button) event.getSource()).getText()){
            case "save":
                System.out.println("ok");
                break;
            case "open":
                File openedFile = fileChooser.showOpenDialog(primaryStage);
                if (openedFile != null){
                    System.out.println(openedFile.getName());
                    try {
                        Map map = new MapInputStream(openedFile).readMap();
                        intersectionsFinder.setMap(map);
                    }catch (FileNotFoundException e) {return;}
                }
                break;
            default:
                System.out.println("ko");
                break;
        }
    }
}
