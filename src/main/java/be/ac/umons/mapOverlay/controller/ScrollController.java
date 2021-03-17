package be.ac.umons.mapOverlay.controller;

import be.ac.umons.mapOverlay.view.IntersectionsFinderView;
import be.ac.umons.mapOverlay.view.SegmentView;
import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;

public class ScrollController implements EventHandler<ScrollEvent>{

    private final SegmentView segmentView;

    public ScrollController(IntersectionsFinderView intersectionsFinderView) {
        this.segmentView = intersectionsFinderView.getSegmentView();
    }

    public void handle(ScrollEvent event) {
        segmentView.changeScale(event.getDeltaY()*0.014);
        segmentView.drawMap();
    }
}
