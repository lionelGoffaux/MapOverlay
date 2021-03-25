package be.ac.umons.mapOverlay.controller;

import be.ac.umons.mapOverlay.view.gui.IntersectionsFinderView;
import be.ac.umons.mapOverlay.view.gui.SegmentView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;

public class ResizeController implements ChangeListener<Number> {

    private final SegmentView segmentView;
    private final Stage primaryStage;

    public ResizeController(Stage primaryStage, IntersectionsFinderView intersectionsFinderView) {
        this.segmentView = intersectionsFinderView.getSegmentView();
        this.primaryStage = primaryStage;
    }

    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        segmentView.setHeight(primaryStage.getHeight());
        segmentView.setWidth(primaryStage.getWidth()-100);
        segmentView.drawMap();
    }
}
