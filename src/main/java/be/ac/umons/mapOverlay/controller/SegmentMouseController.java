package be.ac.umons.mapOverlay.controller;

import be.ac.umons.mapOverlay.Main;
import be.ac.umons.mapOverlay.view.SegmentView;

public abstract class SegmentMouseController {
    protected boolean isInSegmentView(double x, double y){
        Main app = Main.getApp();
        SegmentView segmentView = app.getIntersectionsFinderView().getSegmentView();

    }
}
