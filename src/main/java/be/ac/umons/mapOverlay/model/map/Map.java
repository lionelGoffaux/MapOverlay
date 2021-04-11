package be.ac.umons.mapOverlay.model.map;

import be.ac.umons.mapOverlay.model.geometry.Point;
import be.ac.umons.mapOverlay.model.geometry.Segment;

import java.util.ArrayList;

public class Map {
    private ArrayList<Segment> segments = new ArrayList<>();
    private double maxX=0, maxY=0;

    /***
     * Retourne la valeur maximal en x dans tous les segments.
     * @return
     */
    public double getMaxX() {
        return maxX;
    }

    /***
     * Retourne la valeur maximal en y dans tous les segments.
     * @return
     */
    public double getMaxY() {
        return maxY;
    }

    /**
     * Ajoute un segment à la carte.
     * @param segment
     */
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

    /**
     * Retourne la liste des segments présent sur la carte.
     * @return
     */
    public ArrayList<Segment> getSegments() {
        return segments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Map map = (Map) o;
        return segments.equals(map.segments);
    }
}
