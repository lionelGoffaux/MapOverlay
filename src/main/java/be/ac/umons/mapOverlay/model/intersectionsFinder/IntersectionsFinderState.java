package be.ac.umons.mapOverlay.model.intersectionsFinder;

import be.ac.umons.mapOverlay.model.map.Map;

public interface IntersectionsFinderState {

    void entry(IntersectionsFinder context);
    void start(IntersectionsFinder context);
    void stepForward(IntersectionsFinder context);
    void findAll(IntersectionsFinder context);
    void setMap(IntersectionsFinder context, Map map);
    void startNewSegment(IntersectionsFinder context, double x, double y);
    void endNewSegment(IntersectionsFinder context, double x, double y);
    void newMap(IntersectionsFinder context);
}
