package be.ac.umons.mapOverlay.model.geometry;

import be.ac.umons.mapOverlay.model.intersectionFinder.IntersectionsFinder;
import be.ac.umons.utils.Utils;

import java.util.ArrayList;

public class Segment implements Comparable<Segment>{
    private Point upperPoint, lowerPoint;

    public Segment(double x1, double y1, double x2, double y2){
        this(new Point(x1, y1), new Point(x2, y2));
    }

    public Segment(Point p1, Point p2){
        upperPoint = p1.isUpperThan(p2) ? p1 : p2;
        lowerPoint = p1.isUpperThan(p2) ? p2: p1;
    }

    public Point getIntersectionOfLine(Segment other){ //TODO:refactor
        if(this.upperPoint.equals(other.upperPoint) || this.upperPoint.equals(other.lowerPoint)){
            return new Point(this.upperPoint.getX(), this.upperPoint.getY());
        }
        if(this.lowerPoint.equals(other.upperPoint) || this.lowerPoint.equals(other.lowerPoint)){
            return new Point(this.lowerPoint.getX(), this.lowerPoint.getY());
        }
        double m1 = this.getSlope();
        double m2 = other.getSlope();
        double p1 = this.upperPoint.getY() - m1 * this.upperPoint.getX();
        double p2 = other.upperPoint.getY() - m2 * other.upperPoint.getX();
        if(Utils.almostEqual(m2-m1, 0) || (m1==Double.POSITIVE_INFINITY && m2==Double.POSITIVE_INFINITY))
            return null;
        double commonX;
        double commonY;
        if(m1==Double.POSITIVE_INFINITY){
            commonX = this.lowerPoint.getX();
            commonY = (m2 * commonX + p2);
        }
        else if(m2==Double.POSITIVE_INFINITY){
            commonX = other.lowerPoint.getX();
            commonY = (m1 * commonX + p1);
        }
        else{
            commonX = (p1 - p2) / (m2 - m1);
            commonY = (m1 * commonX + p1);
        }

        return new  Point(commonX, commonY);
    }

    public Point getIntersectionPoint(Segment other){ // TODO: refactor
        Point intersection = getIntersectionOfLine(other);
        if (intersection == null || !contains(intersection) || !other.contains(intersection)) return null;
        return intersection;
        /*Point u1 = this.getVector();
        Point v1 = new Segment(this.lowerPoint, intersection).getVector();
        Point u2 = other.getVector();
        Point v2 = new Segment(other.lowerPoint, intersection).getVector();
        return  u1.isOriented(v1)
                && u2.isOriented(v2)
                && u1.getNorm() >= v1.getNorm()
                && u2.getNorm() >= v2.getNorm() ? intersection : null;*/

    }

    public boolean contains(Point point){ // TODO: refactor
        /*Point u1 = this.getVector();
        Point v1 = new Segment(this.lowerPoint, point).getVector();
        return u1.isOriented(v1) && u1.getNorm() >= v1.getNorm();*/

        return point.compareTo(upperPoint) >= 0 && point.compareTo(lowerPoint) <=0 && lineContains(point);
    }

    private boolean lineContains(Point point) {
        return getA() * point.getX() + getB() * point.getY() == getC();
    }

    private double getA(){
        return -upperPoint.getY() + lowerPoint.getY();
    }

    private double getB(){
        return upperPoint.getX() - lowerPoint.getX();
    }

    private double getC() {
        return getA() * upperPoint.getX() + getB() * upperPoint.getY();
    }

    private Point getVector(){
        return new Point(upperPoint.getX() - lowerPoint.getX(), upperPoint.getY() - lowerPoint.getY());
    }

    private double getSlope(){
        if (this.upperPoint.getX() - this.lowerPoint.getX()==0) return Double.POSITIVE_INFINITY;
        return (this.upperPoint.getY() - this.lowerPoint.getY()) / (this.upperPoint.getX() - this.lowerPoint.getX());
    }



    @Override
    public String toString() {
        return "Segment{" +
                "upperPoint=" + upperPoint +
                ", endPoint=" + lowerPoint +
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
        Segment sweepLine = new Segment(0, sweepLineY, 1, sweepLineY);

        Point a = getIntersectionOfLine(sweepLine);
        Point b = o.getIntersectionOfLine(sweepLine);


        /*if (a == null && b == null){    // les deux ne touchent pas la sweep line
            return lowerPoint.compareTo(o.getLowerPoint()); // compare leur lower point .
        } else if (a == null){ // seul b touche la SL
            return 1;   // a est plus grand que b.
        } else if (b == null){ // seul a touche la SL
            return -1;  // b est plus grand que a.
        }

        int res = -a.compareTo(b);
        if(res == 0){
            res = lowerPoint.getX() <= o.getLowerPoint().getX()? 1: -1; // si les deux sont égaux
            // on compare les lowers points.
        }*/ // TODO: clean

        if (a == null) a = getLowerPoint();
        if (b == null) b = o.getLowerPoint();
        if (a.equals(b)) {
            if (getLowerPoint().compareX(a) == 0 && o.getLowerPoint().compareTo(b) == 0){
                // les deux segments sont horizontaux.
                return a.compareX(b);
            }
            else if (getLowerPoint().compareX(a) == 0){
                return 1;
            }
            else if (o.getLowerPoint().compareTo(b) == 0) {
                return -1;
            }
            else {
                a = getLowerPoint();
                b = o.getLowerPoint();
            }
        }

        return a.compareX(b);
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
