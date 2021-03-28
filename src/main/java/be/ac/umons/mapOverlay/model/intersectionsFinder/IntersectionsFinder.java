package be.ac.umons.mapOverlay.model.intersectionsFinder;

import be.ac.umons.mapOverlay.model.event.Event;
import be.ac.umons.mapOverlay.model.event.EventQueue;
import be.ac.umons.mapOverlay.model.geometry.Line;
import be.ac.umons.mapOverlay.model.geometry.Point;
import be.ac.umons.mapOverlay.model.geometry.Segment;
import be.ac.umons.mapOverlay.model.map.*;
import be.ac.umons.mapOverlay.model.sweepLine.GetLCVisitor;
import be.ac.umons.mapOverlay.model.sweepLine.GetLeftNeighbourVisitor;
import be.ac.umons.mapOverlay.model.sweepLine.GetRightNeighbourVisitor;
import be.ac.umons.mapOverlay.model.sweepLine.SweepLineStatus;
import be.ac.umons.utils.observer.IntersectionsFinderEvent;
import be.ac.umons.utils.observer.Publisher;

import java.util.ArrayList;

public class IntersectionsFinder extends Publisher {

    protected static IntersectionsFinder instance;
    protected Map map = new Map();
    protected EventQueue eventQueue;
    protected SweepLineStatus status;
    protected double sweepLineY = 0;
    protected Point eventPoint = new Point(0, 0);
    protected ArrayList<Point> intersections;
    protected Point newSegmentStart;

    private IntersectionsFinderState state;

    protected IntersectionsFinder() {
        setState(EditionState.getInstance());
    }

    public static IntersectionsFinder getInstance(){
        if(instance==null)
            instance = new IntersectionsFinder();
        return instance;
    }

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
        return new Line(0, sweepLineY, 1, sweepLineY);
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
        ArrayList<Segment> u = e.getU();
        GetLCVisitor glcv = new GetLCVisitor(e.getPoint());
        status.accept(glcv);

        ArrayList<Segment>  l = glcv.getL();
        ArrayList<Segment>  c = glcv.getC();

        if (u.size() + l.size() + c.size() > 1){
            intersections.add(e.getPoint());
        }

        status.suppressAll(l);
        status.suppressAll(c);

        sweepLineY = e.getPoint().getY();
        eventPoint = e.getPoint();

        status.insertAll(u);
        status.insertAll(c);

        ArrayList<Segment> uc = new ArrayList<Segment>(u);
        uc.addAll(c);

        GetLeftNeighbourVisitor glnv;
        GetRightNeighbourVisitor grnv;

        if(uc.isEmpty()){
            glnv = new GetLeftNeighbourVisitor(e.getPoint());
            grnv = new GetRightNeighbourVisitor(e.getPoint());
            status.accept(glnv);
            status.accept(grnv);
            Segment sl = glnv.getNeighbour();
            Segment sr = grnv.getNeighbour();
            if (sr != null && sl != null) findNewEvent(sl, sr, e.getPoint());
        } else {
            Segment sp = Segment.getLeftest(uc);
            glnv = new GetLeftNeighbourVisitor(sp);
            status.accept(glnv);
            Segment sl = glnv.getNeighbour();
            if (sl != null) findNewEvent(sl, sp, e.getPoint());
            Segment spp = Segment.getRightest(uc);
            grnv =  new GetRightNeighbourVisitor(spp);
            status.accept(grnv);
            Segment sr = grnv.getNeighbour();
            if (sr != null) findNewEvent(sr, spp, e.getPoint());
        }
    }

    protected void findNewEvent(Segment sl, Segment sr, Point point) {
        Point p = sl.getIntersection(sr);
        if (p!=null&&p.compareTo(point) > 0) eventQueue.insert(new Event(p));
    }

}
