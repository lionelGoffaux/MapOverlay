package be.ac.umons.mapOverlay.controller;

import be.ac.umons.mapOverlay.Main;
import be.ac.umons.mapOverlay.model.intersectionFinder.IntersectionsFinder;
import be.ac.umons.mapOverlay.view.SegmentView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;

public class ScrollController implements ChangeListener<Number>, EventHandler<ScrollEvent>{

    private final SegmentView segmentView;

    public ScrollController(SegmentView segmentView, IntersectionsFinder intersectionsFinder) {
        this.segmentView = segmentView;
    }

    protected double scale(double x){
        Main app = Main.getApp();
        SegmentView segmentView = app.getIntersectionsFinderView().getSegmentView();
        return x/segmentView.getScale();
    }

    @Override
    public void changed(ObservableValue observable, Number oldValue, Number newValue) {
        Stage primaryStage = Main.getApp().getPrimaryStage();
        segmentView.setHeight(primaryStage.getHeight());
        segmentView.setWidth(primaryStage.getWidth()-100);
        segmentView.draw();
    }

    public void handle(ScrollEvent event) {
        segmentView.changeScale(event.getDeltaY()*0.014);
        segmentView.draw();
    }

}
