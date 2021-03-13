package be.ac.umons.mapOverlay.controller;

import be.ac.umons.mapOverlay.Main;
import be.ac.umons.mapOverlay.model.intersectionFinder.IntersectionsFinder;
import be.ac.umons.mapOverlay.view.SegmentView;

public abstract class SegmentMouseController {

    protected final IntersectionsFinder intersectionsFinder;

    public SegmentMouseController(IntersectionsFinder intersectionsFinder){
        this.intersectionsFinder =  intersectionsFinder;
    }

    protected boolean isInSegmentView(double x, double y){
        Main app = Main.getApp();
        SegmentView segmentView = app.getIntersectionsFinderView().getSegmentView();
        return x>=0 && y>=0 && x <= segmentView.getWidth() && y <= segmentView.getHeight();
    }

    protected double scale(double x){
        Main app = Main.getApp();
        SegmentView segmentView = app.getIntersectionsFinderView().getSegmentView();
        return x/segmentView.getScale();
    }
}
