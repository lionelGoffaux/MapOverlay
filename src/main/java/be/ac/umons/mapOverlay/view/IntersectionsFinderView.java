package be.ac.umons.mapOverlay.view;

import be.ac.umons.mapOverlay.controller.ButtonController;
import be.ac.umons.mapOverlay.controller.MouseClickController;
import be.ac.umons.mapOverlay.model.intersectionFinder.IntersectionsFinder;
import be.ac.umons.utils.observer.IntersectionsFinderEvent;
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

    public void setMouseController(MouseClickController mouseClickController){
        segmentView.setOnMousePressed(mouseClickController);
        segmentView.setOnMouseReleased(mouseClickController);
    }

    public void setButtonController(ButtonController buttonController){
        controlView.setButtonController(buttonController);
    }

    public SegmentView getSegmentView() {
        return segmentView;
    }

    @Override
    public void update(IntersectionsFinderEvent e) {
        segmentView.update(e);
    }
}
