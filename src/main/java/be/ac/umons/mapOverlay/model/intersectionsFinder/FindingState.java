package be.ac.umons.mapOverlay.model.intersectionsFinder;

import be.ac.umons.mapOverlay.model.event.EventQueue;
import be.ac.umons.mapOverlay.model.sweepLine.SweepLineStatus;
import be.ac.umons.mapOverlay.model.event.Event;
import be.ac.umons.mapOverlay.model.map.Map;
import be.ac.umons.mapOverlay.model.geometry.Point;
import be.ac.umons.mapOverlay.model.geometry.Segment;

public class FindingState implements IntersectionsFinderState {

    private static IntersectionsFinderState instance;

    private FindingState(){}
    public static IntersectionsFinderState getInstance() {
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
            context.handleEventPoint(context.eventQueue.getNextEvent());
    }

    @Override
    public void findAll(IntersectionsFinder context) {
        while (!context.eventQueue.isEmpty())
            context.handleEventPoint(context.eventQueue.getNextEvent());
        //System.out.println("context.intersections.size() = " + context.intersections.size());
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
}
