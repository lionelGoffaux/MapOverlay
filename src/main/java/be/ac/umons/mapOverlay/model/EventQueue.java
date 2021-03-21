package be.ac.umons.mapOverlay.model;

import be.ac.umons.sdd2.AVLTree;

public class EventQueue extends AVLTree<Event> {

    public void insertEmpty(Event event) {
        setData(event);
        setLeft(new EventQueue());
        setRight(new EventQueue());
    }

    @Override
    public void insert(Event event) {
        if(isEmpty()) {
            insertEmpty(event);
            return;
        }

        Event current = getData();
        if(current.equals(event)) {
            current.updateSegments(event.getU());
        }
        else super.insert(event);
    }

    public Event getNextEvent() {
        return suppressMin();
    }
}
