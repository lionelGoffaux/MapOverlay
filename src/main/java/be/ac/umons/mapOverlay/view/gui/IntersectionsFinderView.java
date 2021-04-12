package be.ac.umons.mapOverlay.view.gui;

import be.ac.umons.mapOverlay.controller.ButtonController;
import be.ac.umons.mapOverlay.controller.MouseClickController;
import be.ac.umons.mapOverlay.model.intersectionsFinder.IntersectionsFinder;
import be.ac.umons.utils.observer.IntersectionsFinderEvent;
import be.ac.umons.utils.observer.Subscriber;
import javafx.scene.layout.BorderPane;

/**
 * Panel contenant les deux vues.
 */
public class IntersectionsFinderView extends BorderPane implements Subscriber {

    private final SegmentView segmentView;
    private final ButtonView buttonView;

    public IntersectionsFinderView(IntersectionsFinder intersectionsFinder) {
        super();
        segmentView = new SegmentView(600, 600, intersectionsFinder);
        buttonView = new ButtonView();
        setCenter(segmentView);
        setRight(buttonView);
    }

    /**
     * Attribue le mouseClickController au segmentView.
     * @param mouseClickController le controller.
     */
    public void setMouseController(MouseClickController mouseClickController){
        segmentView.setOnMousePressed(mouseClickController);
        segmentView.setOnMouseReleased(mouseClickController);
    }

    /**
     * Attribue le buttonClickController au buttonView.
     * @param buttonController le controller.
     */
    public void setButtonController(ButtonController buttonController){
        buttonView.setButtonController(buttonController);
    }

    /**
     * retourne le segmentView.
     * @return le segmentView.
     */
    public SegmentView getSegmentView() {
        return segmentView;
    }

    @Override
    public void update(IntersectionsFinderEvent e) {
        segmentView.update(e);
    }
}
