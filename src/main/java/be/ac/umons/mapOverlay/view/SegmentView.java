package be.ac.umons.mapOverlay.view;

import be.ac.umons.mapOverlay.model.IntersectionsFinder;
import be.ac.umons.mapOverlay.model.map.Point;
import be.ac.umons.mapOverlay.model.map.Segment;
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


    private void drawMap(){
        GraphicsContext context = getGraphicsContext2D();

        double max = intersectionsFinder.getMap().getMaxX();
        double maxY = intersectionsFinder.getMap().getMaxY();
        if(maxY > max) max = maxY;
        if(max == 0) max=1;
        double scale = getHeight()/(max*1.2);
        System.out.println("scale = " + scale);

        context.clearRect(0, 0, getWidth(), getHeight());
        context.setLineWidth(1.0);

        System.out.println(intersectionsFinder.getSegments().size());



        for(Segment segment: intersectionsFinder.getSegments()){
            Point upper = segment.getUpperPoint(), lower = segment.getLowerPoint();
            double x1 = upper.getX()*scale, y1 = upper.getY()*scale;
            double x2 = lower.getX()*scale, y2 = lower.getY()*scale;
            context.strokeLine(x1, y1, x2, y2);
        }

    }

    public void draw(){
        drawMap();
    }
}
