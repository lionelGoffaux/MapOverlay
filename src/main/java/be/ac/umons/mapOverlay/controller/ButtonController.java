package be.ac.umons.mapOverlay.controller;

import be.ac.umons.mapOverlay.model.IntersectionsFinder;
import be.ac.umons.mapOverlay.model.map.Map;
import be.ac.umons.mapOverlay.model.map.MapInputStream;
import be.ac.umons.mapOverlay.model.map.MapOutputStream;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

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
                File saveFile = fileChooser.showSaveDialog(primaryStage);
                if (saveFile != null){
                    try {
                        MapOutputStream mos = new MapOutputStream(saveFile);
                        mos.writeMap(intersectionsFinder.getMap());
                        mos.close();
                    } catch (IOException e) {
                        e.printStackTrace();//TODO: error message
                    }
                }
                break;
            case "open":
                File openedFile = fileChooser.showOpenDialog(primaryStage);
                if (openedFile != null){
                    try {
                        MapInputStream mis = new MapInputStream(openedFile);
                        Map map = mis.readMap();
                        intersectionsFinder.setMap(map);
                        mis.close();
                    }catch (IOException e) {return;} //TODO: error message
                }
                break;
            case "new":
                intersectionsFinder.createNewMap();
                break;
            default:
                System.out.println("ko");
                break;
        }
    }
}
