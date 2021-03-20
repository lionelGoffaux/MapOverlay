package be.ac.umons.mapOverlay.model;

import be.ac.umons.mapOverlay.model.map.Event;
import be.ac.umons.sdd2.AVLTree;

public class EventQueue extends AVLTree<Event> {

    @Override
    public void insert(Event event) {
        if (!isEmpty()) {
            Event current = getData();
            if (current.equals(event)) current.updateSegments(event.getU());
        }
        else super.insert(event);
    }

    public Event getNextEvent() {
        return suppressMin();
    }
}
