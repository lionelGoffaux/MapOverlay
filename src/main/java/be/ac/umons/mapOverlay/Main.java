package be.ac.umons.mapOverlay;

import be.ac.umons.mapOverlay.controller.ButtonController;
import be.ac.umons.mapOverlay.controller.SegmentController;
import be.ac.umons.mapOverlay.controller.SegmentMousePressedController;
import be.ac.umons.mapOverlay.controller.SegmentMouseReleasedController;
import be.ac.umons.mapOverlay.model.IntersectionsFinder;
import be.ac.umons.mapOverlay.view.IntersectionsFinderView;
import be.ac.umons.mapOverlay.view.SegmentView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Main app;

    private final IntersectionsFinder intersectionsFinder = new IntersectionsFinder();
    private Stage primaryStage;
    private SegmentController segmentController;
    private IntersectionsFinderView intersectionsFinderView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        app = this;
        this.primaryStage = primaryStage;
        SegmentMousePressedController smpc = new SegmentMousePressedController(intersectionsFinder);
        SegmentMouseReleasedController smrc = new SegmentMouseReleasedController(intersectionsFinder);
        ButtonController buttonController = new ButtonController(primaryStage, intersectionsFinder);
        intersectionsFinderView = new IntersectionsFinderView(buttonController,
                intersectionsFinder, smpc, smrc);

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

    public IntersectionsFinderView getIntersectionsFinderView() {
        return intersectionsFinderView;
    }
}
