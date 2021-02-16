package be.ac.umons.mapOverlay.model;

import be.ac.umons.mapOverlay.model.map.Map;
import be.ac.umons.mapOverlay.model.map.Point;
import be.ac.umons.mapOverlay.utils.observer.Publisher;
import be.ac.umons.sdd2.AVLTree;

public class IntersectionsFinder extends Publisher {

    private AVLTree<Point> qTree = new AVLTree<Point>();
    private double sweepLineY = 0;
    private Map map = new Map();

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
        notifySubscribers();
    }

    public double getSweepLineY() {
        return sweepLineY;
    }
}
