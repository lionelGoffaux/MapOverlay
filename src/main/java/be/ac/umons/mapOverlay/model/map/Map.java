package be.ac.umons.mapOverlay.model.map;

import java.util.ArrayList;

public class Map {
    private ArrayList<Segment> segments = new ArrayList<>();

    public void addSegment(Segment segment){
        segments.add(segment);
    }
}
