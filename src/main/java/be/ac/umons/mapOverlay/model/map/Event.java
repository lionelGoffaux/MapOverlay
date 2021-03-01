package be.ac.umons.mapOverlay.model.map;

import java.util.ArrayList;

public class Event implements Comparable<Event>{

    private final Point point;
    private final ArrayList<Segment> segments;

    public Event(Point p){
        this(p, null);
    }

    public Event(Point p, Segment s){
        point = p;
        segments = new ArrayList<Segment>();
        segments.add(s);
    }

    @Override
    public int compareTo(Event o) {
        return point.compareTo(o.point);
    }

    public Point getPoint() {
        return point;
    }

    public ArrayList<Segment> getU() {
        return segments;
    }
}
