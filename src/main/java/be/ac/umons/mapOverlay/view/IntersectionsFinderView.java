package be.ac.umons.mapOverlay.view;

import be.ac.umons.mapOverlay.controller.ButtonController;
import be.ac.umons.mapOverlay.controller.SegmentMouseController;
import be.ac.umons.mapOverlay.model.intersectionFinder.IntersectionsFinder;
import be.ac.umons.utils.observer.Subscriber;
import javafx.scene.layout.BorderPane;

public class IntersectionsFinderView extends BorderPane implements Subscriber {

    private final SegmentView segmentView;

    public IntersectionsFinderView(ButtonController buttonController, IntersectionsFinder intersectionsFinder,
                                   SegmentMouseController smc) {
        super();
        this.segmentView = new SegmentView(600, 600, intersectionsFinder, smc);
        setCenter(segmentView);
        setRight(new ControlView(buttonController));
    }

    public SegmentView getSegmentView() {
        return segmentView;
    }

    @Override
    public void update() {
        segmentView.redraw();
    }
}
