package be.ac.umons.mapOverlay.model;

import be.ac.umons.mapOverlay.model.map.*;
import be.ac.umons.utils.observer.Publisher;

import java.util.ArrayList;

public class IntersectionsFinder extends Publisher {

    private Map map = new Map();
    private EventQueue qTree;
    private SweepLineStatus<Segment> status;
    private ArrayList<Point> intersection;

    private double sweepLineY = 0;
    private boolean findingInProgress = false;
    private Point newSegmentStart;

    public void stepForward(){
        if(!findingInProgress)
            initFinding();

        if(!qTree.isEmpty()){
            nextEvent();
        }
    }

    private void initFinding(){
        findingInProgress = true;
        status = new SweepLineStatus<Segment>();
        qTree = new EventQueue();
        intersection = new ArrayList<Point>();

        for (Segment s: map.getSegments()) {
            qTree.insert(new Event(s.getUpperPoint(), s));
            qTree.insert(new Event(s.getLowerPoint()));
        }
    }

    private void handleEventPoint(Event e){
        ArrayList<Segment>  u = e.getU();
        ArrayList<Segment>  l = status.getL();
        ArrayList<Segment>  c = status.getC();

        if (u.size() + l.size() + c.size() > 1){
            intersection.add(e.getPoint());
        }

        status.suppressAll(l);
        status.suppressAll(c);

        status.insertAll(u);
        status.insertAll(c);

        if(u.isEmpty() && c.isEmpty()){
            Segment sl = status.getLeftNeighbour(e.getPoint());
            Segment sr = status.getRightNeighbour(e.getPoint());
            if (sr != null && sl != null) findNewEvent(sl, sr, e.getPoint());
        } else {
            Segment sp = getLeft(u, c);
            Segment sl = status.getLeftNeighbour(sp);
            if (sl != null) findNewEvent(sl, sp, e.getPoint());
            Segment spp = getRight(u, c);
            Segment sr = status.getRightNeighbour(spp);
            if (sr != null) findNewEvent(sr, spp, e.getPoint());
        }
    }

    private Segment getLeft(ArrayList<Segment> u, ArrayList<Segment> c) {
        Segment l = null;
        for(Segment s: u)
            if(l == null || l.compareTo(s) >= 0) l = s;

        for(Segment s: c)
            if(l == null || l.compareTo(s) >= 0) l = s;

        return l;
    }

    private Segment getRight(ArrayList<Segment> u, ArrayList<Segment> c) {
        Segment r = null;
        for(Segment s: u)
            if(r == null || r.compareTo(s) <= 0) r = s;

        for(Segment s: c)
            if(r == null || r.compareTo(s) <= 0) r = s;

        return r;
    }

    private void findNewEvent(Segment sl, Segment sr, Point point) {
        Point p = sl.getIntersectionOfLine(sr);
        if (p.compareTo(point) > 0) qTree.insert(new Event(p));
    }

    private void nextEvent(){
        handleEventPoint(qTree.getNextEvent());
    }

    private void findAll(){
        if(!findingInProgress)
            initFinding();

        while (!qTree.isEmpty())
            nextEvent();
    }

    public Map getMap() {
        return map;
    }

    public ArrayList<Segment> getSegments(){
        return map.getSegments();
    }

    public void setMap(Map map) {
        this.map = map;
        notifySubscribers();
    }

    public double getSweepLineY() {
        return sweepLineY;
    }

    public void setNewSegmentStart(double x, double y){
        this.newSegmentStart = new Point(x, y);
    }

    public void setNewSegmentEnd(double x, double y) {
        if(!findingInProgress){
            map.addSegment(new Segment(newSegmentStart, new Point(x, y)));
            notifySubscribers();
        }
    }

    public void createNewMap() {
        if(!findingInProgress){
            map = new Map();
            notifySubscribers();
        }
    }
}
