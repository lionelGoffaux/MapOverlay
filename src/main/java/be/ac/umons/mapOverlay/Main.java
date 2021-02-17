package be.ac.umons.mapOverlay;

import be.ac.umons.mapOverlay.controller.ButtonController;
import be.ac.umons.mapOverlay.controller.SegmentController;
import be.ac.umons.mapOverlay.model.IntersectionsFinder;
import be.ac.umons.mapOverlay.view.IntersectionsFinderView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Main app;

    private final IntersectionsFinder intersectionsFinder = new IntersectionsFinder();
    private Stage primaryStage;
    private SegmentController segmentController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        app = this;
        this.primaryStage = primaryStage;
        ButtonController buttonController = new ButtonController(primaryStage, intersectionsFinder);
        IntersectionsFinderView intersectionsFinderView = new IntersectionsFinderView(buttonController, intersectionsFinder);

        primaryStage.setTitle("Map Overlay");
        primaryStage.setScene(new Scene(intersectionsFinderView));
        primaryStage.setResizable(true);

        segmentController = new SegmentController(intersectionsFinderView.getSegmentView());
        primaryStage.widthProperty().addListener(segmentController);
        primaryStage.heightProperty().addListener(segmentController);
        intersectionsFinderView.setOnScroll(segmentController);

        intersectionsFinder.subscribe(intersectionsFinderView);

        primaryStage.show();
    }

    public static Main getApp() {
        return app;
    }

    public double getSweepLineY(){
        return intersectionsFinder.getSweepLineY();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public SegmentController getSegmentController() {
        return segmentController;
    }
}
