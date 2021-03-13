package be.ac.umons.mapOverlay.model.intersectionFinder;

import be.ac.umons.mapOverlay.model.map.Map;
import be.ac.umons.mapOverlay.model.map.Point;
import be.ac.umons.mapOverlay.model.map.Segment;

import java.util.ArrayList;

public class EditionState implements IntersectionFinderState{

    private static IntersectionFinderState instance;

    private EditionState(){}
    public static IntersectionFinderState getInstance() {
        if(instance == null) instance = new EditionState();
        return instance;
    }

    @Override
    public void entry(IntersectionsFinder context) {
        context.sweepLineY = 0;
        context.intersections = new ArrayList<>();
    }

    @Override
    public void start(IntersectionsFinder context) {
        context.setState(FindingState.getInstance());
    }

    @Override
    public void stepForward(IntersectionsFinder context) {

    }

    @Override
    public void findAll(IntersectionsFinder context) {

    }

    @Override
    public void setMap(IntersectionsFinder context, Map map) {
        context.map = map;
    }

    @Override
    public void startNewSegment(IntersectionsFinder context, double x, double y) {
        context.newSegmentStart = new Point(x, y);
    }

    @Override
    public void endNewSegment(IntersectionsFinder context, double x, double y) {
        Point start = context.newSegmentStart;
        if(start != null){
            Map map =  context.map;
            map.addSegment(new Segment(start, new Point(x, y)));
            context.newSegmentStart = null;
        }
    }

    @Override
    public void newMap(IntersectionsFinder context) {
        context.map = new Map();
    }
}
