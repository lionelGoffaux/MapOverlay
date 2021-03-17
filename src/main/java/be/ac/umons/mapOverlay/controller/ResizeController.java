package be.ac.umons.mapOverlay.controller;

import be.ac.umons.mapOverlay.Main;
import be.ac.umons.mapOverlay.view.IntersectionsFinderView;
import be.ac.umons.mapOverlay.view.SegmentView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;

public class ResizeController implements ChangeListener<Number> {

    private final SegmentView segmentView;

    public ResizeController(IntersectionsFinderView intersectionsFinderView) {
        this.segmentView = intersectionsFinderView.getSegmentView();
    }

    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        Stage primaryStage = Main.getApp().getPrimaryStage();
        segmentView.setHeight(primaryStage.getHeight());
        segmentView.setWidth(primaryStage.getWidth()-100);
        segmentView.draw();
    }
}
