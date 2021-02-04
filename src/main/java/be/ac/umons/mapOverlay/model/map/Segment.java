package be.ac.umons.mapOverlay.model.map;

public class Segment {
    private Point upperPoint, endPoint;

    public Segment(double x1, double y1, double x2, double y2){
        this(new Point(x1, y1), new Point(x2, y2));
    }

    public Segment(Point p1, Point p2){
        upperPoint = p1;
        endPoint = p2;
    }
}
