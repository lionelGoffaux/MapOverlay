package be.ac.umons.mapOverlay.controller;

import be.ac.umons.mapOverlay.model.intersectionFinder.IntersectionsFinder;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class SegmentMouseReleasedController extends SegmentMouseController implements EventHandler<MouseEvent> {
    public SegmentMouseReleasedController(IntersectionsFinder intersectionsFinder) {
        super(intersectionsFinder);
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY){
            double x = event.getX(), y = event.getY();
            intersectionsFinder.endNewSegment(scale(x), scale(y));
        }
    }
}
