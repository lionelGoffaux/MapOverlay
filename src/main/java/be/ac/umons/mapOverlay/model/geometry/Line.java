package be.ac.umons.mapOverlay.model.geometry;

import be.ac.umons.mapOverlay.model.intersectionsFinder.IntersectionsFinder;
import be.ac.umons.utils.Utils;

public class Line implements Comparable<Segment>{
    protected final Point upperPoint, lowerPoint;
    protected final double a, b, c;

    public Line(double x1, double y1, double x2, double y2){
        this(new Point(x1, y1), new Point(x2, y2));
    }

    public Line(Point p1, Point p2){
        upperPoint = p1.isUpperThan(p2) ? p1 : p2;
        lowerPoint = p1.isUpperThan(p2) ? p2: p1;
        a = -lowerPoint.getY() + upperPoint.getY();
        b = lowerPoint.getX() - upperPoint.getX();
        c = a * upperPoint.getX() + b * upperPoint.getY();
    }

    public Point getIntersection(Line other){
        double det = getDet(this, other);
        if(det==0) return null;

        double commonX = (other.b*c - b* other.c)/det;
        double commonY = (-other.a*c + a*other.c)/det;
        Point intersection =  new Point(commonX, commonY);
//        if (other.contains(intersection))
            return intersection;
//        return null;
    }

    protected boolean contains(Point point) {
        return Utils.almostEqual(a*point.getX() + b*point.getY(), c);
    }

    @Override
    public int compareTo(Segment o) {
        Line sweepLine = IntersectionsFinder.getInstance().getSweepLine();

        Point a = getIntersection(sweepLine);
        Point b = o.getIntersection(sweepLine);

        if (a==null) {
            if(b==null)
                return lowerPoint.compareX(o.lowerPoint);
            else return 1;
        }
        else if (b==null) return -1;

        if (a.equals(b)) {
            a = lowerPoint;
            b = o.lowerPoint;
        }

        return a.compareX(b);
    }

    protected static double getDet(Line s1, Line s2){
        return s1.a*s2.b-s2.a*s1.b;
    }
}
