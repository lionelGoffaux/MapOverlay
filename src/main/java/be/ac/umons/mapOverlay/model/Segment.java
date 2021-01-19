package be.ac.umons.mapOverlay.model;

public class Segment {
    private Point upperPoint, endPoint;

    public Segment(double x1, double y1, double x2, double y2) throws SegmentPointOrderException {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    public Segment(Point p1, Point p2) throws SegmentPointOrderException{
        if(p2.isUpperThan(p1)){
            throw new SegmentPointOrderException("the upper point is lower than the end point");
        }
        upperPoint = p1;
        endPoint = p2;
    }
}
