package be.ac.umons.mapOverlay.model.geometry;

import java.util.ArrayList;

public class Segment extends Line {

    public Segment(double x1, double y1, double x2, double y2){
        this(new Point(x1, y1), new Point(x2, y2));
    }

    public Segment(Point p1, Point p2){
        super(p1, p2);
    }

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
    public String toString() {
        return "Segment{" +
                "upperPoint=" + upperPoint +
                ", lowerPoint=" + lowerPoint +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Segment segment = (Segment) o;
        return upperPoint.equals(segment.upperPoint) && lowerPoint.equals(segment.lowerPoint);
    }

    public Point getUpperPoint() {
        return upperPoint;
    }

    public Point getLowerPoint() {
        return lowerPoint;
    }

    public static Segment getLeftest(ArrayList<Segment> segments){
        Segment l = null;
        for(Segment s: segments)
            if(l == null || l.compareTo(s) >= 0) l = s;

        return l;
    }

    public static Segment getRightest(ArrayList<Segment> segments){
        Segment r = null;
        for(Segment s: segments)
            if(r == null || r.compareTo(s) <= 0) r = s;
        return r;
    }
}
