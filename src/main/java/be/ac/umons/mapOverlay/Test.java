package be.ac.umons.mapOverlay;

import be.ac.umons.mapOverlay.controller.ButtonController;
import be.ac.umons.mapOverlay.controller.MouseClickController;
import be.ac.umons.mapOverlay.controller.ResizeController;
import be.ac.umons.mapOverlay.controller.ScrollController;
import be.ac.umons.mapOverlay.model.intersectionsFinder.IntersectionsFinder;
import be.ac.umons.mapOverlay.model.map.Map;
import be.ac.umons.mapOverlay.model.map.MapInputFile;
import be.ac.umons.mapOverlay.view.cli.IntersectionsFinderCli;
import be.ac.umons.mapOverlay.view.gui.IntersectionsFinderView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Lanceur de l'application.
 */
public class Test extends Application {

    public static void main(String[] args) {
        if(args.length != 0){
            IntersectionsFinder intersectionsFinder = IntersectionsFinder.getInstance();
            boolean bench = args.length==2 && args[1].equals("--bench");
            if(!bench) new IntersectionsFinderCli(intersectionsFinder);

            try {
                MapInputFile mif = new MapInputFile(args[0]);
                Map map = mif.readMap();
                mif.close();
                long start = System.currentTimeMillis();
                intersectionsFinder.setMap(map);
                intersectionsFinder.findAll();
                long stop = System.currentTimeMillis();
                if(bench){
                    System.out.println(map.getSegments().size()
                            +","+ intersectionsFinder.getIntersections().size()
                            +","+ (stop-start) );
                }
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
    public void start(Stage primaryStage){
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
