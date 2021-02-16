package be.ac.umons.mapOverlay.model;

import be.ac.umons.mapOverlay.model.map.Point;
import be.ac.umons.sdd2.AVLTree;

public class IntersectionsFinder {

    private AVLTree<Point> qTree = new AVLTree<Point>();
    private double sweepLineY = 0;

    public double getSweepLineY() {
        return sweepLineY;
    }
}
