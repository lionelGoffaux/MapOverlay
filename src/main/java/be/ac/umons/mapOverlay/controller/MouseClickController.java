package be.ac.umons.mapOverlay.controller;

import be.ac.umons.mapOverlay.model.intersectionsFinder.IntersectionsFinder;
import be.ac.umons.mapOverlay.view.gui.IntersectionsFinderView;
import be.ac.umons.mapOverlay.view.gui.SegmentView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class MouseClickController implements EventHandler<MouseEvent> {

    protected final IntersectionsFinder intersectionsFinder;
    private final SegmentView segmentView;

    public MouseClickController(IntersectionsFinder intersectionsFinder,
                                IntersectionsFinderView intersectionsFinderView){
        this.intersectionsFinder =  intersectionsFinder;
        this.segmentView = intersectionsFinderView.getSegmentView();
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY){
            double x = event.getX(), y = event.getY();

            if (event.getEventType() == MouseEvent.MOUSE_PRESSED)
                intersectionsFinder.startNewSegment(segmentView.scale(x), segmentView.scale(y));
            else if(event.getEventType() == MouseEvent.MOUSE_RELEASED)
                intersectionsFinder.endNewSegment(segmentView.scale(x), segmentView.scale(y));
        }
    }
}
