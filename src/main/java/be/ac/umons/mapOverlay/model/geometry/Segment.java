package be.ac.umons.mapOverlay.model.geometry;

import be.ac.umons.mapOverlay.model.intersectionsFinder.IntersectionsFinder;

import java.util.ArrayList;

public class Segment extends Line implements Comparable<Segment>{

    public Segment(double x1, double y1, double x2, double y2){
        this(new Point(x1, y1), new Point(x2, y2));
    }

    public Segment(Point p1, Point p2){
        super(p1, p2);
    }

    /***
     * Retourne l'intersection avec un autre segment.
     * @param other
     * @return
     */
    public Point getIntersection(Segment other){
        if(this.upperPoint.equals(other.upperPoint) || this.upperPoint.equals(other.lowerPoint)){
            return new Point(this.upperPoint.getX(), this.upperPoint.getY());
        }

        if(this.lowerPoint.equals(other.upperPoint) || this.lowerPoint.equals(other.lowerPoint)){
            return new Point(this.lowerPoint.getX(), this.lowerPoint.getY());
        }

        Point intersection = super.getIntersection(other);
        if (intersection == null || !contains(intersection) || !other.contains(intersection)) return null;
        return intersection;
    }

    @Override
    public boolean contains(Point point){
        return point.compareTo(upperPoint) >= 0 && point.compareTo(lowerPoint) <=0 && super.contains(point);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Segment segment = (Segment) o;
        return upperPoint.equals(segment.upperPoint) && lowerPoint.equals(segment.lowerPoint);
    }

    @Override
    public int compareTo(Segment o) {
        Line sweepLine = IntersectionsFinder.getInstance().getSweepLine();
        Point p1 = getIntersection(sweepLine);
        Point p2 = o.getIntersection(sweepLine);

        if(p1==null && p2==null) return upperPoint.compareX(o.upperPoint);

        if(p1==null){
            p1 = IntersectionsFinder.getInstance().getEventPoint();
            if(p1.equals(p2)) return 1;
            return p1.compareX(p2);
        }

        if(p2==null) {
            p2 = IntersectionsFinder.getInstance().getEventPoint();
            if (p1.equals(p2)) return -1;
            return p1.compareX(p2);
        }

        if (p1.equals(p2)) {
            double minY = lowerPoint.compareY(o.lowerPoint) > 0? o.lowerPoint.getY(): lowerPoint.getY();
            Line sl = new Line(0, minY, 1, minY);
            p1 = sl.getIntersection(this);
            p2 = sl.getIntersection(o);
        }
        return p1.compareX(p2);
    }

    /***
     * Retourne le point au dessus dans l'ordre de passage de la sweep line.
     * @return
     */
    public Point getUpperPoint() {
        return upperPoint;
    }

    /***
     * Retourne le point en bas dans l'ordre de passage de la sweep line.
     * @return
     */
    public Point getLowerPoint() {
        return lowerPoint;
    }

    /***
     * Trouve le segment le plus à gauche dans la liste de segment.
     * @param segments
     * @return
     */
    public static Segment getLeftest(ArrayList<Segment> segments){
        Segment l = null;
        for(Segment s: segments)
            if(l == null || l.compareTo(s) >= 0) l = s;

        return l;
    }

    /***
     * Trouve le segment le plus à droite dans la liste de segment.
     * @param segments
     * @return
     */
    public static Segment getRightest(ArrayList<Segment> segments){
        Segment r = null;
        for(Segment s: segments)
            if(r == null || r.compareTo(s) <= 0) r = s;
        return r;
    }
}
