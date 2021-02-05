package be.ac.umons.mapOverlay.model.map;

import static java.lang.Math.abs;

public class Segment {
    private Point upperPoint, lowerPoint;

    public Segment(double x1, double y1, double x2, double y2){
        this(new Point(x1, y1), new Point(x2, y2));
    }

    public Segment(Point p1, Point p2){
        upperPoint = p1.isUpperThan(p2) ? p1 : p2;
        lowerPoint = p1.isUpperThan(p2) ? p2: p1;
    }

    public Point isIntersecting(Segment other){
        double m1 = (this.upperPoint.getY() - this.lowerPoint.getY()) / (this.upperPoint.getX() - this.lowerPoint.getX());
        double m2 = (other.upperPoint.getY() - other.lowerPoint.getY()) / (other.upperPoint.getX() - other.lowerPoint.getX());
        double p1 = this.upperPoint.getY() - m1 * this.upperPoint.getX();
        double p2 = other.upperPoint.getY() - m2 * other.upperPoint.getX();
        if(almostEqual(m2-m1, 0))
            return null;
        double commonX = (p1 - p2) / (m2 - m1);
        double commonY = (m1 * commonX + p1);
        Point intersection = new Point(commonX, commonY);
        Point u1 = this.getVector();
        Point v1 = new Segment(this.upperPoint, intersection).getVector();
        Point u2 = other.getVector();
        Point v2 = new Segment(other.upperPoint, intersection).getVector();
        return  almostEqual((u1.scalarProduct(v1))/(u1.norm()*v1.norm()), 1.0)
                && almostEqual((u2.scalarProduct(v2))/(u2.norm()*v2.norm()), 1.0)
                && u1.norm() > v1.norm() && u2.norm() > v2.norm() ? intersection : null;
    }

    private Point getVector(){
        return new Point(upperPoint.getX() - lowerPoint.getX(), upperPoint.getY() - lowerPoint.getY());
    }

    private static boolean almostEqual(double u, double v){
        return abs(u-v) < 1e-8;
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
}
