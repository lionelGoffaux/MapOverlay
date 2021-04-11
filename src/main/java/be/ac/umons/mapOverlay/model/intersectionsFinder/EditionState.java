package be.ac.umons.mapOverlay.model.intersectionsFinder;

import be.ac.umons.mapOverlay.model.geometry.Point;
import be.ac.umons.mapOverlay.model.geometry.Segment;
import be.ac.umons.mapOverlay.model.map.Map;

import java.util.ArrayList;

/**
 * État d'édition de carte.
 */
public class EditionState implements IntersectionsFinderState {

    private static IntersectionsFinderState instance;

    private EditionState(){}
    public static IntersectionsFinderState getInstance() {
        if(instance == null) instance = new EditionState();
        return instance;
    }

    @Override
    public void entry(IntersectionsFinder context) {
        context.sweepLineY = 0;
        context.intersections = new ArrayList<>();
    }

    @Override
    public void stepForward(IntersectionsFinder context) {
        context.setState(FindingState.getInstance());
        FindingState.getInstance().stepForward(context);
    }

    @Override
    public void findAll(IntersectionsFinder context) {
        context.setState(FindingState.getInstance());
        FindingState.getInstance().findAll(context);
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
