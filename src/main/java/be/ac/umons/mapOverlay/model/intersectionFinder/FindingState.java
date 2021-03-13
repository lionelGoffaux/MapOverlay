package be.ac.umons.mapOverlay.model.intersectionFinder;

import be.ac.umons.mapOverlay.model.EventQueue;
import be.ac.umons.mapOverlay.model.SweepLineStatus;
import be.ac.umons.mapOverlay.model.map.Event;
import be.ac.umons.mapOverlay.model.map.Map;
import be.ac.umons.mapOverlay.model.map.Point;
import be.ac.umons.mapOverlay.model.map.Segment;

import java.util.ArrayList;

public class FindingState implements IntersectionFinderState{

    private static IntersectionFinderState instance;

    private FindingState(){}
    public static IntersectionFinderState getInstance() {
        if (instance == null) instance = new FindingState();
        return instance;
    }

    @Override
    public void entry(IntersectionsFinder context) {
        context.status = new SweepLineStatus();
        EventQueue eq = new EventQueue();

        for (Segment s: context.getSegments()) {
            eq.insert(new Event(s.getUpperPoint(), s));
            eq.insert(new Event(s.getLowerPoint()));
        }

        context.eventQueue = eq;
    }

    @Override
    public void start(IntersectionsFinder context) {

    }

    @Override
    public void stepForward(IntersectionsFinder context) {
        if(!context.eventQueue.isEmpty())
            handleEventPoint(context, context.eventQueue.getNextEvent());
    }

    @Override
    public void findAll(IntersectionsFinder context) {
        while (!context.eventQueue.isEmpty())
            handleEventPoint(context, context.eventQueue.getNextEvent());
    }

    @Override
    public void setMap(IntersectionsFinder context, Map map) {
        context.map = map;
        context.setState(EditionState.getInstance());
    }

    @Override
    public void startNewSegment(IntersectionsFinder context, double x, double y) {
        context.newSegmentStart = new Point(x, y);
        context.setState(EditionState.getInstance());
    }

    @Override
    public void endNewSegment(IntersectionsFinder context, double x, double y) {

    }

    @Override
    public void newMap(IntersectionsFinder context) {
        context.map = new Map();
        context.setState(EditionState.getInstance());
    }

    private void handleEventPoint(IntersectionsFinder context,  Event e){
        SweepLineStatus status = context.status;
        context.sweepLineY = e.getPoint().getY();
        ArrayList<Segment> u = e.getU();
        ArrayList<Segment>  l = status.getL(e.getPoint());
        ArrayList<Segment>  c = status.getC(e.getPoint());

        if (u.size() + l.size() + c.size() > 1){
            context.intersections.add(e.getPoint());
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
            if (sr != null && sl != null) findNewEvent(context, sl, sr, e.getPoint());
        } else {
            Segment sp = Segment.getLeftest(uc);
            Segment sl = status.getLeftNeighbour(sp);
            if (sl != null) findNewEvent(context, sl, sp, e.getPoint());
            Segment spp = Segment.getRightest(uc);
            Segment sr = status.getRightNeighbour(spp);
            if (sr != null) findNewEvent(context, sr, spp, e.getPoint());
        }
    }

    private void findNewEvent(IntersectionsFinder context, Segment sl, Segment sr, Point point) {
        Point p = sl.getIntersectionOfLine(sr);
        if (p.compareTo(point) > 0) context.eventQueue.insert(new Event(p));
    }
}
