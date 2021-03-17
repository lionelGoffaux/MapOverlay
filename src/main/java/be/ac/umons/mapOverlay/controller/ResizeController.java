package be.ac.umons.mapOverlay.controller;

import be.ac.umons.mapOverlay.Main;
import be.ac.umons.mapOverlay.view.IntersectionsFinderView;
import be.ac.umons.mapOverlay.view.SegmentView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;

public class ResizeController implements ChangeListener<Number> {

    private final SegmentView segmentView;
    private final Main app;

    public ResizeController(IntersectionsFinderView intersectionsFinderView, Main app) {
        this.segmentView = intersectionsFinderView.getSegmentView();
        this.app = app;
    }

    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        Stage primaryStage = app.getPrimaryStage();
        segmentView.setHeight(primaryStage.getHeight());
        segmentView.setWidth(primaryStage.getWidth()-100);
        segmentView.draw();
    }
}
