package be.ac.umons.mapOverlay.model.event;

import be.ac.umons.sdd2.AVLTree;

/**
 * Structure de donnée contenant les points d'évènements.
 */
public class EventQueue extends AVLTree<Event> {

    public void insertEmpty(Event event) {
        setData(event);
        setLeft(new EventQueue());
        setRight(new EventQueue());
    }

    @Override
    public void insert(Event event) {
        if(!isEmpty() && getData().equals(event)) {
            getData().updateSegments(event.getU());
        }
        else super.insert(event);
    }

    /***
     * Retourne le prochain event à traiter.
     * @return l'événement suivant.
     */
    public Event getNextEvent() {
        return suppressMin();
    }
}
