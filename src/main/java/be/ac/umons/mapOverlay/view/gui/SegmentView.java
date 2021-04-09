package be.ac.umons.mapOverlay.view.gui;

import be.ac.umons.mapOverlay.model.intersectionsFinder.IntersectionsFinder;
import be.ac.umons.mapOverlay.model.geometry.Point;
import be.ac.umons.mapOverlay.model.geometry.Segment;
import be.ac.umons.utils.observer.IntersectionsFinderEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SegmentView extends Canvas { //TODO: refactor scale system

    private final IntersectionsFinder intersectionsFinder;
    private double scale = 1;

    public SegmentView(double width, double height, IntersectionsFinder intersectionsFinder) {
        super(width, height);
        this.intersectionsFinder = intersectionsFinder;
    }

    public double scale(double x){
        return x/scale;
    }

    private void rescale(){
        double maxX = intersectionsFinder.getMap().getMaxX();
        double maxY = intersectionsFinder.getMap().getMaxY();
        if(maxY > maxX) maxX = maxY;
        if(maxX == 0) maxX=1;
        scale = getHeight()/(maxX*1.2);
    }



    public void drawMap(){
        GraphicsContext context = getGraphicsContext2D();
        context.setLineWidth(3.0);

        context.clearRect(0, 0, getWidth(), getHeight());

        double sweepLineY = IntersectionsFinder.getInstance().getSweepLineY()*scale;
        context.setStroke(Color.RED);
        context.strokeLine(0, sweepLineY, getWidth(), sweepLineY);
        context.setStroke(Color.BLACK);

        for(Segment segment: intersectionsFinder.getSegments()){
            Point upper = segment.getUpperPoint(), lower = segment.getLowerPoint();
            double x1 = upper.getX()*scale, y1 = upper.getY()*scale;
            double x2 = lower.getX()*scale, y2 = lower.getY()*scale;
            context.strokeLine(x1, y1, x2, y2);
        }
        for(Point p : IntersectionsFinder.getInstance().getIntersections()){
            context.setStroke(Color.YELLOW);
            double x = p.getX()*scale;
            double y = p.getY()*scale;
            context.strokeOval(x, y, 3, 3);
            context.setStroke(Color.BLACK);
        }

    }

    public void changeScale(double delta, double mouseX, double mouseY){
        scale+=delta;
    }


    public void update(IntersectionsFinderEvent e){
        switch (e){
            case END_NEW_SEGMENT:
            case SET_MAP:
            case CREATE_NEW_MAP:
                rescale();

            case STEP_FORWARD:
            case FIND_ALL:
                drawMap();
                break;
        }
    }
}
