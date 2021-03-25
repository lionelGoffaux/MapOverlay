package be.ac.umons.mapOverlay;

import be.ac.umons.mapOverlay.controller.*;
import be.ac.umons.mapOverlay.model.intersectionsFinder.IntersectionsFinder;
import be.ac.umons.mapOverlay.model.map.Map;
import be.ac.umons.mapOverlay.model.map.MapInputFile;
import be.ac.umons.mapOverlay.view.cli.IntersectionsFinderCli;
import be.ac.umons.mapOverlay.view.gui.IntersectionsFinderView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    // TODO: null pointer getleft (map WTF et Horizontal)
    // TODO: j'ai l'impression que il a pas toutes les intersections (à verifier)
    // TODO: doc

    public static void main(String[] args) {
        if(args.length != 0){ //TODO: on garde?
            IntersectionsFinder intersectionsFinder = IntersectionsFinder.getInstance();
            IntersectionsFinderCli intersectionsFinderCli = new IntersectionsFinderCli(intersectionsFinder);

            try {
                MapInputFile mif = new MapInputFile(args[0]);
                Map map = mif.readMap();
                mif.close();
                intersectionsFinder.setMap(map);
                intersectionsFinder.start();
                intersectionsFinder.findAll();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
        else{
            launch(args);
        }
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
