package be.ac.umons.mapOverlay.controller;

import be.ac.umons.mapOverlay.Main;
import be.ac.umons.mapOverlay.model.intersectionFinder.IntersectionsFinder;
import be.ac.umons.mapOverlay.view.SegmentView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class MouseClickController implements EventHandler<MouseEvent> {

    protected final IntersectionsFinder intersectionsFinder;
    private final Main app;

    public MouseClickController(IntersectionsFinder intersectionsFinder, Main app){
        this.intersectionsFinder =  intersectionsFinder;
        this.app = app;
    }

    private double scale(double x){ // TODO: move to view
        SegmentView segmentView = app.getIntersectionsFinderView().getSegmentView();
        return x/segmentView.getScale();
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY){
            double x = event.getX(), y = event.getY();

            if (event.getEventType() == MouseEvent.MOUSE_PRESSED)
                intersectionsFinder.startNewSegment(scale(x), scale(y));
            else if(event.getEventType() == MouseEvent.MOUSE_RELEASED)
                intersectionsFinder.endNewSegment(scale(x), scale(y));
        }
    }
}
