package be.ac.umons.mapOverlay;

import be.ac.umons.mapOverlay.controller.ButtonController;
import be.ac.umons.mapOverlay.view.IntersectionsFinderView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        ButtonController buttonController = new ButtonController(primaryStage);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(new Scene(new IntersectionsFinderView(buttonController)));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
