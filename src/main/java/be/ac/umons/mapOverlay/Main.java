package be.ac.umons.mapOverlay;

import be.ac.umons.mapOverlay.controller.*;
import be.ac.umons.mapOverlay.model.intersectionFinder.IntersectionsFinder;
import be.ac.umons.mapOverlay.view.IntersectionsFinderView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Main app;

    private final IntersectionsFinder intersectionsFinder = new IntersectionsFinder();
    private Stage primaryStage;
    private IntersectionsFinderView intersectionsFinderView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        app = this;
        this.primaryStage = primaryStage;


        intersectionsFinderView = new IntersectionsFinderView(intersectionsFinder);

        primaryStage.setTitle("Map Overlay");
        primaryStage.setScene(new Scene(intersectionsFinderView));
        primaryStage.setResizable(true);

        ButtonController buttonController = new ButtonController(primaryStage, intersectionsFinder);
        ScrollController scrollController = new ScrollController(intersectionsFinderView.getSegmentView(), intersectionsFinder);
        SegmentMouseController segmentMouseController = new SegmentMouseController(intersectionsFinder);

        primaryStage.widthProperty().addListener(scrollController);
        primaryStage.heightProperty().addListener(scrollController);
        intersectionsFinderView.setOnScroll(scrollController);
        intersectionsFinderView.setMouseController(segmentMouseController);
        intersectionsFinderView.setButtonController(buttonController);

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

    public IntersectionsFinderView getIntersectionsFinderView() {
        return intersectionsFinderView;
    }
}
