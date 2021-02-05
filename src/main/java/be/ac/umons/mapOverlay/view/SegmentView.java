package be.ac.umons.mapOverlay.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SegmentView extends Canvas {

    public SegmentView(double width, double height) {
        super(width, height);
        GraphicsContext graphics_context = this.getGraphicsContext2D();

        // set fill for rectangle
        graphics_context.setFill(Color.RED);
        graphics_context.fillRect(0, 0, width, height);

        // set fill for oval
        graphics_context.setFill(Color.BLUE);
        graphics_context.fillOval(30, 30, 70, 70);
    }
}
