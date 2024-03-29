package be.ac.umons.mapOverlay.model.geometry;

import be.ac.umons.utils.Utils;

/**
 * Classe représentant un point.
 */
public class Point implements Comparable<Point> {
    private final double x, y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * Retourne la coordonnée en X du point.
     * @return la coordonnée en X
     */
    public double getX() {
        return x;
    }

    /**
     * Retourne la coordonnée en Y du point.
     * @return la coordonnée en Y.
     */
    public double getY() {
        return y;
    }

    /***
     * Retourne si un point est avant dans l'ordre de passage de la sweep line.
     * @param p le point à comparer.
     * @return vrai si p est en dessous, faux sinon.
     */
    public boolean isUpperThan(Point p){
        return compareTo(p) <= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Utils.almostEqual(point.x, x) && Utils.almostEqual(point.y, y);
    }

    /***
     * compare à un autre point en fonction des X.
     * @param o le point à comparer
     * @return 0 si les x sont égaux, -1 si o est à gauche, 1 sinon.
     */
    public int compareX(Point o){
        if (Utils.almostEqual(x, o.x)) return 0;
        return x > o.x ? 1: -1;
    }

    /***
     * compare à un autre point en fonction des Y.
     * @param o le point à comparer.
     * @return 0 si les y sont égaux, -1 si o est en haut, 1 sinon.
     */
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

    @Override
    public String toString() {
        return x + " " + y;
    }

}
