package be.ac.umons.mapOverlay.model.intersectionFinder;

import be.ac.umons.mapOverlay.model.EventQueue;
import be.ac.umons.mapOverlay.model.SweepLineStatus;
import be.ac.umons.mapOverlay.model.map.*;
import be.ac.umons.utils.observer.Publisher;

import java.util.ArrayList;

public class IntersectionsFinder extends Publisher {

    protected Map map = new Map();
    protected EventQueue eventQueue;
    protected SweepLineStatus status;
    protected double sweepLineY = 0;
    protected ArrayList<Point> intersections;
    protected Point newSegmentStart;

    private IntersectionFinderState state;

    public IntersectionsFinder() {
        setState(EditionState.getInstance());
    }

    protected void setState(IntersectionFinderState state) {
        this.state = state;
        state.entry(this);
    }


    public void start(){
        state.start(this);
        notifySubscribers();
    }

    public void stepForward(){
        state.stepForward(this);
        notifySubscribers();
    }

    public void findAll(){
        state.findAll(this);
        notifySubscribers();
    }

    public void setMap(Map map) {
        state.setMap(this, map);
        notifySubscribers();
    }

    public void startNewSegment(double x, double y){
        state.startNewSegment(this, x, y);
        notifySubscribers();
    }

    public void endNewSegment(double x, double y) {
        state.endNewSegment(this, x, y);
        notifySubscribers();
    }

    public void createNewMap() {
        state.newMap(this);
        notifySubscribers();
    }

    public double getSweepLineY() {
        return sweepLineY;
    }

    public Map getMap() {
        return map;
    }

    public ArrayList<Segment> getSegments(){
        return map.getSegments();
    }

}
