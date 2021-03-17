package be.ac.umons.mapOverlay.view;

import be.ac.umons.mapOverlay.model.intersectionFinder.IntersectionsFinder;
import be.ac.umons.mapOverlay.model.map.Point;
import be.ac.umons.mapOverlay.model.map.Segment;
import be.ac.umons.utils.observer.IntersectionsFinderEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

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
        double max = intersectionsFinder.getMap().getMaxX();
        double maxY = intersectionsFinder.getMap().getMaxY();
        if(maxY > max) max = maxY;
        if(max == 0) max=1;
        scale = getHeight()/(max*1.2);
    }



    public void drawMap(){
        GraphicsContext context = getGraphicsContext2D();
        context.setLineWidth(3.0);
        // TODO : drawsweepline DORIANO

        context.clearRect(0, 0, getWidth(), getHeight());



        for(Segment segment: intersectionsFinder.getSegments()){
            Point upper = segment.getUpperPoint(), lower = segment.getLowerPoint();
            double x1 = upper.getX()*scale, y1 = upper.getY()*scale;
            double x2 = lower.getX()*scale, y2 = lower.getY()*scale;
            context.strokeLine(x1, y1, x2, y2);
        }

    }

    public void changeScale(double delta){
        scale+=delta;
    }


    public void update(IntersectionsFinderEvent e){
        switch (e){
            case END_NEW_SEGMENT:
            case SET_MAP:
            case CREATE_NEW_MAP:
                rescale();

            case START:
            case STEP_FORWARD:
            case FIND_ALL:
                drawMap();
                break;
        }
    }
}
