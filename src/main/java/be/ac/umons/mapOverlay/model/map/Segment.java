package be.ac.umons.mapOverlay.model.map;

import be.ac.umons.mapOverlay.Main;

import static java.lang.Math.abs;
import be.ac.umons.mapOverlay.Utils;

public class Segment implements Comparable<Segment>{
    private Point upperPoint, lowerPoint;

    public Segment(double x1, double y1, double x2, double y2){
        this(new Point(x1, y1), new Point(x2, y2));
    }

    public Segment(Point p1, Point p2){
        upperPoint = p1.isUpperThan(p2) ? p1 : p2;
        lowerPoint = p1.isUpperThan(p2) ? p2: p1;
    }

    public Point getIntersectionOfLine(Segment other){
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

    public Point getIntersectionPoint(Segment other){
        Point intersection = getIntersectionOfLine(other);
        if (intersection == null) return null;
        Point u1 = this.getVector();
        Point v1 = new Segment(this.lowerPoint, intersection).getVector();
        Point u2 = other.getVector();
        Point v2 = new Segment(other.lowerPoint, intersection).getVector();
        return  u1.isOriented(v1)
                && u2.isOriented(v2)
                && u1.getNorm() >= v1.getNorm()
                && u2.getNorm() >= v2.getNorm() ? intersection : null;
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

        double sweepLineY =  Main.getApp().getSweepLineY();
        Segment sweepLine = new Segment(0, sweepLineY, 1, sweepLineY);

        Point a = getIntersectionOfLine(sweepLine);
        Point b = o.getIntersectionOfLine(sweepLine);
        // TODO: a ou b null?

        return a.compareTo(b);
    }
}
