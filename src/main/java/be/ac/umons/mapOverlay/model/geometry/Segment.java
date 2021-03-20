package be.ac.umons.mapOverlay.model.geometry;

import be.ac.umons.mapOverlay.model.intersectionFinder.IntersectionsFinder;
import be.ac.umons.utils.Utils;

import java.util.ArrayList;

public class Segment implements Comparable<Segment>{
    // TODO : class line?
    private final Point upperPoint, lowerPoint;
    protected final double a, b, c;

    public Segment(double x1, double y1, double x2, double y2){
        this(new Point(x1, y1), new Point(x2, y2));
    }

    public Segment(Point p1, Point p2){
        upperPoint = p1.isUpperThan(p2) ? p1 : p2;
        lowerPoint = p1.isUpperThan(p2) ? p2: p1;
        a = getA();
        b = getB();
        c = getC();
    }

    public Point getIntersectionOfLine(Segment other){

        double det = getDet(this, other);
        if(det==0) return null;

        double oa = other.getA();
        double ob = other.getB();

        double commonX = (ob*c - b* other.c)/det;
        double commonY = (-oa*c + a*other.c)/det;

        return new Point(commonX, commonY);
    }

    public Point getIntersectionPoint(Segment other){
        if(this.upperPoint.equals(other.upperPoint) || this.upperPoint.equals(other.lowerPoint)){
            return new Point(this.upperPoint.getX(), this.upperPoint.getY());
        }
        if(this.lowerPoint.equals(other.upperPoint) || this.lowerPoint.equals(other.lowerPoint)){
            return new Point(this.lowerPoint.getX(), this.lowerPoint.getY());
        }

        Point intersection = getIntersectionOfLine(other);
        if (intersection == null || !contains(intersection) || !other.contains(intersection)) return null;
        return intersection;
    }

    public boolean contains(Point point){
        return point.compareTo(upperPoint) >= 0 && point.compareTo(lowerPoint) <=0 && lineContains(point);
    }

    protected boolean lineContains(Point point) {
        return a*point.getX() + b*point.getY() == c;
    }

    private double getA(){
        return -lowerPoint.getY() + upperPoint.getY();
    }

    private double getB(){
        return lowerPoint.getX() - upperPoint.getX();
    }

    private double getC() {
        return getA() * upperPoint.getX() + getB() * upperPoint.getY();
    }

    private double getSlope(){
        if (this.upperPoint.getX() - this.lowerPoint.getX()==0) return Double.POSITIVE_INFINITY;
        return (this.upperPoint.getY() - this.lowerPoint.getY()) / (this.upperPoint.getX() - this.lowerPoint.getX());
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

    @Override
    public int compareTo(Segment o) {

        double sweepLineY =  IntersectionsFinder.getInstance().getSweepLineY();
        Segment sweepLine = new Segment(0, sweepLineY, 1, sweepLineY); // TODO: get SL

        Point a = getIntersectionOfLine(sweepLine);
        Point b = o.getIntersectionOfLine(sweepLine);

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

    public Point getUpperPoint() {
        return upperPoint;
    }

    public Point getLowerPoint() {
        return lowerPoint;
    }

    public static double getDet(Segment a, Segment b){
        return a.getA()*b.getB()-b.getA()* a.getB();
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
