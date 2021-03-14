package be.ac.umons.mapOverlay.view;

import be.ac.umons.mapOverlay.controller.ButtonController;
import be.ac.umons.mapOverlay.controller.SegmentMouseController;
import be.ac.umons.mapOverlay.model.intersectionFinder.IntersectionsFinder;
import be.ac.umons.utils.observer.Subscriber;
import javafx.scene.layout.BorderPane;

public class IntersectionsFinderView extends BorderPane implements Subscriber {

    private final SegmentView segmentView;
    private final ControlView controlView;

    public IntersectionsFinderView(IntersectionsFinder intersectionsFinder) {
        super();
        segmentView = new SegmentView(600, 600, intersectionsFinder);
        controlView = new ControlView();
        setCenter(segmentView);
        setRight(controlView);
    }

    public void setMouseController(SegmentMouseController segmentMouseController){
        segmentView.setOnMousePressed(segmentMouseController);
        segmentView.setOnMouseReleased(segmentMouseController);
    }

    public void setButtonController(ButtonController buttonController){
        controlView.setButtonController(buttonController);
    }

    public SegmentView getSegmentView() {
        return segmentView;
    }

    @Override
    public void update() {
        segmentView.redraw();
    }
}
