package be.ac.umons.mapOverlay;

import be.ac.umons.mapOverlay.controller.ButtonController;
import be.ac.umons.mapOverlay.model.IntersectionsFinder;
import be.ac.umons.mapOverlay.view.IntersectionsFinderView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Main app;

    private IntersectionsFinder intersectionsFinder = new IntersectionsFinder();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        app = this;
        ButtonController buttonController = new ButtonController(primaryStage);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(new Scene(new IntersectionsFinderView(buttonController)));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static Main getApp() {
        return app;
    }

    public double getSweepLineY(){
        return intersectionsFinder.getSweepLineY();
    }

    public IntersectionsFinder getIntersectionsFinder() {
        return intersectionsFinder;
    }
}
