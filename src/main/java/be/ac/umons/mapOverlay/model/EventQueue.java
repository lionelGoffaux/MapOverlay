package be.ac.umons.mapOverlay.model;

import be.ac.umons.mapOverlay.model.map.Event;
import be.ac.umons.sdd2.AVLTree;

public class EventQueue extends AVLTree<Event> {

    public Event getNextEvent() {
        Event min =  searchMin();
        suppress(min);
        return min;
    }
}
