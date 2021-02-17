package be.ac.umons.mapOverlay.view;

import be.ac.umons.mapOverlay.controller.ButtonController;
import be.ac.umons.mapOverlay.model.IntersectionsFinder;
import be.ac.umons.utils.observer.Subscriber;
import javafx.scene.layout.BorderPane;

public class IntersectionsFinderView extends BorderPane implements Subscriber {

    private final SegmentView segmentView;

    public IntersectionsFinderView(ButtonController buttonController, IntersectionsFinder intersectionsFinder) {
        super();
        this.segmentView = new SegmentView(600, 600, intersectionsFinder);
        setCenter(segmentView);
        setRight(new ControlView(buttonController));
    }

    @Override
    public void update() {
        segmentView.draw();
    }
}
