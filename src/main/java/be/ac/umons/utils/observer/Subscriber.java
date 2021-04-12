package be.ac.umons.utils.observer;

/**
 * Classe Subscriber utile au design pattern Publisher
 */
public interface Subscriber {
    /**
     * Notifie les subscriber qu'une modification à eu lieu.
     * @param e un événement.
     */
    void update(IntersectionsFinderEvent e);
}
