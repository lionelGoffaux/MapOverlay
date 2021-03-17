package be.ac.umons.mapOverlay.model.intersectionFinder;

import be.ac.umons.mapOverlay.model.EventQueue;
import be.ac.umons.mapOverlay.model.SweepLineStatus;
import be.ac.umons.mapOverlay.model.map.*;
import be.ac.umons.utils.observer.IntersectionsFinderEvent;
import be.ac.umons.utils.observer.Publisher;

import java.util.ArrayList;

public class IntersectionsFinder extends Publisher {

    private static IntersectionsFinder instance;
    protected Map map = new Map();
    protected EventQueue eventQueue;
    protected SweepLineStatus status;
    protected double sweepLineY = 0;
    protected ArrayList<Point> intersections;
    protected Point newSegmentStart;

    private IntersectionFinderState state;

    private IntersectionsFinder() {
        setState(EditionState.getInstance());
    }

    public static IntersectionsFinder getInstance(){
        if(instance==null)
            instance = new IntersectionsFinder();
        return instance;
    }

    protected void setState(IntersectionFinderState state) {
        this.state = state;
        state.entry(this);
    }


    public void start(){
        state.start(this);
        notifySubscribers(IntersectionsFinderEvent.START);
    }

    public void stepForward(){
        state.stepForward(this);
        notifySubscribers(IntersectionsFinderEvent.STEP_FORWARD);
    }

    public void findAll(){
        state.findAll(this);
        notifySubscribers(IntersectionsFinderEvent.FIND_ALL);
    }

    public void setMap(Map map) {
        state.setMap(this, map);
        notifySubscribers(IntersectionsFinderEvent.SET_MAP);
    }

    public void startNewSegment(double x, double y){
        state.startNewSegment(this, x, y);
        notifySubscribers(IntersectionsFinderEvent.START_NEW_SEGMENT);
    }

    public void endNewSegment(double x, double y) {
        state.endNewSegment(this, x, y);
        notifySubscribers(IntersectionsFinderEvent.END_NEW_MAP);
    }

    public void createNewMap() {
        state.newMap(this);
        notifySubscribers(IntersectionsFinderEvent.CREATE_NEW_MAP);
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
