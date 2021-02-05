package be.ac.umons.mapOverlay.model.map;
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
        return y > p.y || (y == p.y && x == p.x);
    }

    public double norm(){
        return sqrt(x * x + y * y);
    }

    public double scalarProduct(Point other){
        return this.x * other.x + this.y * other.y;
    }
}
