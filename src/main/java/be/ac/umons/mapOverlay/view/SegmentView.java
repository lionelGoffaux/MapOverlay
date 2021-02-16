package be.ac.umons.mapOverlay.view;

import be.ac.umons.mapOverlay.model.IntersectionsFinder;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SegmentView extends Canvas {

    private final IntersectionsFinder intersectionsFinder;

    public SegmentView(double width, double height, IntersectionsFinder intersectionsFinder) {
        super(width, height);
        this.intersectionsFinder = intersectionsFinder;
        GraphicsContext graphics_context = getGraphicsContext2D();

        // set fill for rectangle
        graphics_context.setFill(Color.RED);
        graphics_context.fillRect(0, 0, width, height);

        // set fill for oval
        graphics_context.setFill(Color.BLUE);
        graphics_context.fillOval(30, 30, 70, 70);
    }
}
