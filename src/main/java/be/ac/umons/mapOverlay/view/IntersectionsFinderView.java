package be.ac.umons.mapOverlay.view;

import javafx.scene.layout.BorderPane;

public class IntersectionsFinderView extends BorderPane {

    public IntersectionsFinderView() {
        super();
        this.setCenter(new SegmentView(600, 600));
        this.setRight(new ControlView());
    }
}
