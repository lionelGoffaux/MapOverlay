package be.ac.umons.mapOverlay.model.map;

import be.ac.umons.mapOverlay.model.geometry.Point;
import be.ac.umons.mapOverlay.model.geometry.Segment;

import java.util.ArrayList;

public class Map {
    private ArrayList<Segment> segments = new ArrayList<>();
    private double maxX=0, maxY=0;

    public double getMaxX() {
        return maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public void addSegment(Segment segment){
        segments.add(segment);
        Point upper = segment.getUpperPoint(), lower = segment.getLowerPoint();
        double x1 = upper.getX(), y1 = upper.getY();
        double x2 = lower.getX(), y2 = lower.getY();
        if(x1>maxX) maxX=x1;
        if(x2>maxX) maxX=x2;
        if(y1>maxY) maxY=y1;
        if(y2>maxY) maxY=y2;
    }

    public ArrayList<Segment> getSegments() {
        return segments;
    }
}
