package be.ac.umons.mapOverlay;

import be.ac.umons.mapOverlay.controller.*;
import be.ac.umons.mapOverlay.model.intersectionFinder.IntersectionsFinder;
import be.ac.umons.mapOverlay.view.IntersectionsFinderView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    // TODO: refactor model package
    // TODO: refactor tests package
    // TODO: tests EventQueue
    // TODO: doc

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){ // TODO: clean controllers
        IntersectionsFinder intersectionsFinder = IntersectionsFinder.getInstance();
        IntersectionsFinderView intersectionsFinderView = new IntersectionsFinderView(intersectionsFinder);

        primaryStage.setTitle("Map Overlay");
        primaryStage.setScene(new Scene(intersectionsFinderView));
        primaryStage.setResizable(true);

        ButtonController buttonController = new ButtonController(primaryStage, intersectionsFinder);
        ScrollController scrollController = new ScrollController(intersectionsFinderView);
        ResizeController resizeController = new ResizeController(primaryStage, intersectionsFinderView);
        MouseClickController mouseClickController = new MouseClickController(intersectionsFinder,
                intersectionsFinderView);

        primaryStage.widthProperty().addListener(resizeController);
        primaryStage.heightProperty().addListener(resizeController);
        intersectionsFinderView.setOnScroll(scrollController);
        intersectionsFinderView.setMouseController(mouseClickController);
        intersectionsFinderView.setButtonController(buttonController);

        intersectionsFinder.subscribe(intersectionsFinderView);

        primaryStage.show();
    }
}
