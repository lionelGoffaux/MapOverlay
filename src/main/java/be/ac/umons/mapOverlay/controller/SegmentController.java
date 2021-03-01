package be.ac.umons.mapOverlay.controller;

import be.ac.umons.mapOverlay.Main;
import be.ac.umons.mapOverlay.view.SegmentView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;

public class SegmentController implements ChangeListener<Number>, EventHandler<ScrollEvent>{

    private final SegmentView segmentView;

    public SegmentController(SegmentView segmentView) {
        this.segmentView = segmentView;
    }

    @Override
    public void changed(ObservableValue observable, Number oldValue, Number newValue) {
        Stage primaryStage = Main.getApp().getPrimaryStage();
        segmentView.setHeight(primaryStage.getHeight());
        segmentView.setWidth(primaryStage.getWidth()-100);
        segmentView.draw();
    }

    @Override
    public void handle(ScrollEvent event) {
        segmentView.changeScale(event.getDeltaY()*0.01);
        segmentView.draw();
    }
}