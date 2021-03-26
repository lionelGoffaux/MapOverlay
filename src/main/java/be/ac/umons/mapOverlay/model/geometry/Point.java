package be.ac.umons.mapOverlay.model.geometry;

import static java.lang.Math.sqrt;
import be.ac.umons.utils.Utils;
import jdk.jshell.execution.Util;

public class Point implements Comparable<Point> {
    private final double x, y;

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
        return compareTo(p) <= 0;
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
        return Utils.almostEqual(point.x, x) && Utils.almostEqual(point.y, y);
    }

    public int compareX(Point o){
        if (Utils.almostEqual(x, o.x)) return 0;
        return x > o.x ? 1: -1;
    }

    public int compareY(Point o){
        if (Utils.almostEqual(y, o.y)) return 0;
        return y > o.y ? 1: -1;
    }

    @Override
    public int compareTo(Point o) {
        if(equals(o)) return 0;
        if(compareY(o) != 0) return compareY(o);
        return compareX(o);
    }

    public boolean isOriented(Point other){
        if(this.equals(new Point(0,0)) || other.equals(new Point(0,0)))
            return true;
        return Utils.almostEqual((this.scalarProduct(other))/(this.getNorm()*other.getNorm()), 1.0);
    }

    @Override
    public String toString() {
        return x + " " + y;
    }

}
