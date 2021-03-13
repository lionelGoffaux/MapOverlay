package be.ac.umons.mapOverlay.model.intersectionFinder;

import be.ac.umons.mapOverlay.model.map.Map;

public interface IntersectionFinderState {

    public void entry(IntersectionsFinder context);
    public void start(IntersectionsFinder context);
    public void stepForward(IntersectionsFinder context);
    public void findAll(IntersectionsFinder context);
    public void setMap(IntersectionsFinder context, Map map);
    public void startNewSegment(IntersectionsFinder context, double x, double y);
    public void endNewSegment(IntersectionsFinder context, double x, double y);
    public void newMap(IntersectionsFinder context);
}
