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
        //System.out.println("=============== EVENT ================");
        //System.out.println("e = " + e);
        sweepLineY = e.getPoint().getY();
        ArrayList<Segment> u = e.getU();
        //System.out.println("===============LC================");
        GetLCVisitor glcv = new GetLCVisitor(e.getPoint());
        status.accept(glcv);
        //System.out.println("===============LC================");

        ArrayList<Segment>  l = glcv.getL();
        ArrayList<Segment>  c = glcv.getC();

        //System.out.println("u.size() = " + u.size());
        //System.out.println("l.size() = " + l.size());
        //System.out.println("c.size() = " + c.size());
        //System.out.println("TEST");
        //if(c.size() >= 1) System.out.println("c.get(0) = " + c.get(0));
        //if(c.size() >= 2) System.out.println("c.get(1) = " + c.get(1));
        if (u.size() + l.size() + c.size() > 1){
            //System.out.println("INTER");
            intersections.add(e.getPoint());
        }
        //System.out.println("END TEST");

        status.suppressAll(l);
        status.suppressAll(c);

        status.insertAll(u);
        status.insertAll(c);

        //System.out.println("status.getData() = " + status.getData());
        //if(!status.isEmpty()) System.out.println("status.getRight().getData() = " + status.getRight().getData());
        //if(!status.isEmpty()) System.out.println("status.getLeft().getData() = " + status.getLeft().getData());

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
            //System.out.println("getleft");
            Segment sp = Segment.getLeftest(uc);
            //System.out.println("leftest = " + sp);
            //System.out.println("-----neighbour-----");
            glnv = new GetLeftNeighbourVisitor(sp);
            status.accept(glnv);
            //System.out.println("--------end--------");
            Segment sl = glnv.getNeighbour();
            //System.out.println("left neighbour = " + sl);
            if (sl != null) findNewEvent(sl, sp, e.getPoint());
            //System.out.println("getright");
            Segment spp = Segment.getRightest(uc);
            //System.out.println("rightest = " + spp);
            //System.out.println("-----neighbour-----");
            grnv =  new GetRightNeighbourVisitor(spp);
            status.accept(grnv);
            //System.out.println("--------end--------");
            Segment sr = grnv.getNeighbour();
            //System.out.println("right neighbour = " + sr);
            if (sr != null) findNewEvent(sr, spp, e.getPoint());
        }
    }

    protected void findNewEvent(Segment sl, Segment sr, Point point) {
        Point p = sl.getIntersection(sr);
        //System.out.println(" new event p = " + p);
        if (p!=null&&p.compareTo(point) > 0) eventQueue.insert(new Event(p));
    }

}
