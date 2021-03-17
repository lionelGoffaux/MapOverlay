package be.ac.umons.mapOverlay;

import be.ac.umons.mapOverlay.controller.*;
import be.ac.umons.mapOverlay.model.intersectionFinder.IntersectionsFinder;
import be.ac.umons.mapOverlay.view.IntersectionsFinderView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    // TODO: refactor model package
    // TODO: refactor tests
    // TODO: doc


    private final IntersectionsFinder intersectionsFinder = IntersectionsFinder.getInstance();
    private Stage primaryStage;
    private IntersectionsFinderView intersectionsFinderView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){ // TODO: clean controllers
        this.primaryStage = primaryStage;


        intersectionsFinderView = new IntersectionsFinderView(intersectionsFinder);

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

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public IntersectionsFinderView getIntersectionsFinderView() {
        return intersectionsFinderView;
    }
}
