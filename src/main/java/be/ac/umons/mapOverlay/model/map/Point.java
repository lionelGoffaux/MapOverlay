package be.ac.umons.mapOverlay.model.map;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Point {
    private double x, y;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public boolean isUpperThan(Point p){
        return y > p.y || (y == p.y && x <= p.x);
    }

    public double getNorm(){
        return sqrt(x * x + y * y);
    }

    public double scalarProduct(Point other){
        return this.x * other.x + this.y * other.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }

    public boolean isOriented(Point other){
        if(this.equals(new Point(0,0)) || other.equals(new Point(0,0)))
            return true;
        return almostEqual((this.scalarProduct(other))/(this.getNorm()*other.getNorm()), 1.0);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    private static boolean almostEqual(double u, double v){
        return abs(u-v) < 1e-5;
    } //TODO code redundancy
}
