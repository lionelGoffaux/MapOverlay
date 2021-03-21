package be.ac.umons.mapOverlay.model;

import be.ac.umons.mapOverlay.model.geometry.Point;
import be.ac.umons.mapOverlay.model.geometry.Segment;

import java.util.ArrayList;
import java.util.Objects;

public class Event implements Comparable<Event>{
    // TODO: NotUpperException ?

    private final Point point;
    private final ArrayList<Segment> segments;

    public Event(Point p){
        this(p, null);
    }

    public Event(Point p, Segment s){
        point = p;
        segments = new ArrayList<>();
        if(s != null) segments.add(s);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return point.equals(event.point);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point);
    }

    @Override
    public int compareTo(Event o) {
        return point.compareTo(o.point);
    }

    public void updateSegments(ArrayList<Segment> newSegments){
        for(Segment s : newSegments){
            if (!segments.contains(s)) segments.add(s);
        }
    }

    public Point getPoint() {
        return point;
    }

    public ArrayList<Segment> getU() {
        return segments;
    }
}
