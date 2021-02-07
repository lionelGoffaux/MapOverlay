package be.ac.umons.mapOverlay.view;

import be.ac.umons.mapOverlay.controller.ButtonController;
import javafx.scene.layout.BorderPane;

public class IntersectionsFinderView extends BorderPane {

    public IntersectionsFinderView(ButtonController buttonController) {
        super();
        setCenter(new SegmentView(600, 600));
        setRight(new ControlView(buttonController));
    }
}
