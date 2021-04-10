package be.ac.umons.mapOverlay.model.geometry;

import be.ac.umons.utils.Utils;

public class Line{
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

    /***
     * Retourne le point d'intersection avec une autre ligne.
     * @param other
     * @return
     */
    public Point getIntersection(Line other){
        double det = getDet(this, other);
        if(det==0) return null;

        double commonX = (other.b*c - b* other.c)/det;
        double commonY = (-other.a*c + a*other.c)/det;
        return new Point(commonX, commonY);
    }

    @Override
    public String toString() {
        return upperPoint + " " + lowerPoint;
    }

    /***
     * Retourne si un point est contenu dans la ligne.
     * @param point
     * @return
     */
    public boolean contains(Point point) {
        return Utils.almostEqual(a*point.getX() + b*point.getY(), c);
    }

    /***
     * Calcule le determinant entre deux segments.
     * @param l1
     * @param l2
     * @return
     */
    public static double getDet(Line l1, Line l2){
        return l1.a*l2.b-l2.a*l1.b;
    }
}
