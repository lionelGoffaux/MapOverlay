package be.ac.umons.utils.observer;

public interface Subscriber {
    /**
     * Notifie les subscriber qu'une modification à eu lieu.
     * @param e
     */
    void update(IntersectionsFinderEvent e);
}
