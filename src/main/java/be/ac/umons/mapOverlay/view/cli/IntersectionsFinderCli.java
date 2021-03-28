package be.ac.umons.mapOverlay.view.cli;

import be.ac.umons.mapOverlay.model.geometry.Point;
import be.ac.umons.mapOverlay.model.intersectionsFinder.IntersectionsFinder;
import be.ac.umons.utils.observer.IntersectionsFinderEvent;
import be.ac.umons.utils.observer.Subscriber;

public class IntersectionsFinderCli implements Subscriber {

    private final IntersectionsFinder intersectionsFinder;

    public IntersectionsFinderCli(IntersectionsFinder intersectionsFinder){
        this.intersectionsFinder = intersectionsFinder;
        intersectionsFinder.subscribe(this);
    }

    @Override
    public void update(IntersectionsFinderEvent e) {
        if(e == IntersectionsFinderEvent.FIND_ALL){
            printIntersections();
        }
    }

    private void printIntersections() {
        for (Point i : intersectionsFinder.getIntersections()){
            //System.out.println(i);
        }
    }
}
