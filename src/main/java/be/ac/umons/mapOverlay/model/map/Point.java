package be.ac.umons.mapOverlay.model.map;

public class Point {
    private double x, y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public boolean isUpperThan(Point p){
        return y > p.y || (y == p.y && x == p.x);
    }
}
