package be.ac.umons.mapOverlay.model.intersectionsFinder;

import be.ac.umons.mapOverlay.model.Event;
import be.ac.umons.mapOverlay.model.EventQueue;
import be.ac.umons.mapOverlay.model.SweepLineStatus;
import be.ac.umons.mapOverlay.model.geometry.Line;
import be.ac.umons.mapOverlay.model.geometry.Point;
import be.ac.umons.mapOverlay.model.geometry.Segment;
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

    private IntersectionsFinderState state;

    private IntersectionsFinder() {
        setState(EditionState.getInstance());
    }

    public static IntersectionsFinder getInstance(){
        if(instance==null)
            instance = new IntersectionsFinder();
        return instance;
    }

    // TODO: get sweep line intersection

    protected void setState(IntersectionsFinderState state) {
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
        notifySubscribers(IntersectionsFinderEvent.END_NEW_SEGMENT);
    }

    public void createNewMap() {
        state.newMap(this);
        notifySubscribers(IntersectionsFinderEvent.CREATE_NEW_MAP);
    }

    public double getSweepLineY() {
        return sweepLineY;
    }

    public Line getSweepLine() {
        Line s = new Line(0, sweepLineY, 1, sweepLineY);
        return s;
    }

    public Map getMap() {
        return map;
    }

    public ArrayList<Segment> getSegments(){
        return map.getSegments();
    }

    public ArrayList<Point> getIntersections() {
        return intersections;
    }

    protected void handleEventPoint(Event e){
        sweepLineY = e.getPoint().getY();
        ArrayList<Segment> u = e.getU();
        ArrayList<Segment>  l = status.getL(e.getPoint());
        ArrayList<Segment>  c = status.getC(e.getPoint());

        if (u.size() + l.size() + c.size() > 1){
            intersections.add(e.getPoint());
        }

        status.suppressAll(l);
        status.suppressAll(c);

        status.insertAll(u);
        status.insertAll(c);

        ArrayList<Segment> uc = new ArrayList<Segment>(u);
        uc.addAll(c);

        if(uc.isEmpty()){
            Segment sl = status.getLeftNeighbour(e.getPoint());
            Segment sr = status.getRightNeighbour(e.getPoint());
            if (sr != null && sl != null) findNewEvent(sl, sr, e.getPoint());
        } else {
            Segment sp = Segment.getLeftest(uc);
            Segment sl = status.getLeftNeighbour(sp);
            if (sl != null) findNewEvent(sl, sp, e.getPoint());
            Segment spp = Segment.getRightest(uc);
            Segment sr = status.getRightNeighbour(spp);
            if (sr != null) findNewEvent(sr, spp, e.getPoint());
        }
    }

    protected void findNewEvent(Segment sl, Segment sr, Point point) {
        Point p = sl.getIntersection(sr);
        if (p!=null&&p.compareTo(point) > 0) eventQueue.insert(new Event(p));
    }

}
