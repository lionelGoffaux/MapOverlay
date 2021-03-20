package be.ac.umons.mapOverlay.controller;

import be.ac.umons.mapOverlay.model.intersectionFinder.IntersectionsFinder;
import be.ac.umons.mapOverlay.model.map.Map;
import be.ac.umons.mapOverlay.model.map.MapInputFile;
import be.ac.umons.mapOverlay.model.map.MapOutputFile;
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

    private void save(){
        File saveFile = fileChooser.showSaveDialog(primaryStage);
        if (saveFile != null){
            try {
                MapOutputFile mos = new MapOutputFile(saveFile);
                mos.writeMap(intersectionsFinder.getMap());
                mos.close();
            } catch (IOException e) {
                e.printStackTrace();//TODO: error message
            }
        }
    }

    private void open(){
        File openedFile = fileChooser.showOpenDialog(primaryStage);
        if (openedFile != null){
            try {
                MapInputFile mis = new MapInputFile(openedFile);
                Map map = mis.readMap();
                intersectionsFinder.setMap(map);
                mis.close();
            }catch (IOException e) {return;} //TODO: error message
        }
    }

    @Override
    public void handle(ActionEvent event) {
        switch (((Button) event.getSource()).getText()){
            case "save":
                save();
                break;
            case "open":
                open();
                break;
            case "new":
                intersectionsFinder.createNewMap();
                break;
            case "step":
                intersectionsFinder.stepForward();
                break;
            case "start":
                intersectionsFinder.start();
                break;
            default:
                System.out.println("ko");
                break;
        }
    }
}
