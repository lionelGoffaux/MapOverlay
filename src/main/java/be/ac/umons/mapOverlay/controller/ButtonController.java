package be.ac.umons.mapOverlay.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ButtonController implements EventHandler<ActionEvent> {

    Stage primaryStage;
    FileChooser fileChooser = new FileChooser();

    public ButtonController(Stage primaryStage){
        this.primaryStage = primaryStage;
        fileChooser.setInitialDirectory(new File("cartes"));
    }

    @Override
    public void handle(ActionEvent event) {
        switch (((Button) event.getSource()).getText()){
            case "save":
                System.out.println("ok");
                break;
            case "open":
                System.out.println("yousk");
                File openFile = fileChooser.showOpenDialog(primaryStage);
                if (openFile != null){
                    System.out.println(openFile.getName());
                }
                break;
            default:
                System.out.println("ko");
                break;
        }
    }
}
