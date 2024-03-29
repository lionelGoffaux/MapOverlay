package be.ac.umons.mapOverlay.controller;

import be.ac.umons.mapOverlay.view.gui.IntersectionsFinderView;
import be.ac.umons.mapOverlay.view.gui.SegmentView;
import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;

/**
 * Classe qui gère les évènements liés au scroll de souris.
 */
public class ScrollController implements EventHandler<ScrollEvent>{

    private final SegmentView segmentView;

    public ScrollController(IntersectionsFinderView intersectionsFinderView) {
        this.segmentView = intersectionsFinderView.getSegmentView();
    }

    @Override
    public void handle(ScrollEvent event) {
        segmentView.changeScale(event.getDeltaY()*0.014);
        segmentView.drawMap();
    }
}
