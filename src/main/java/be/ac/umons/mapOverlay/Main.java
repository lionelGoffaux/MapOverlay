package be.ac.umons.mapOverlay;

import be.ac.umons.mapOverlay.controller.ButtonController;
import be.ac.umons.mapOverlay.view.IntersectionsFinderView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ButtonController buttonController = new ButtonController(primaryStage);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(new Scene(new IntersectionsFinderView(buttonController)));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
