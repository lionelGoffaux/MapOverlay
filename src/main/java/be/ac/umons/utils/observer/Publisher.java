package be.ac.umons.utils.observer;

import java.util.ArrayList;

/**
 * Classe utile au design pattern Publisher
 */
public abstract class Publisher {
    private final ArrayList<Subscriber> subscribers = new ArrayList<>();

    /**
     * Ajoute un subscriber.
     * @param sub
     */
    public void subscribe(Subscriber sub){
        subscribers.add(sub);
    }

    protected void notifySubscribers(IntersectionsFinderEvent e){
        for (Subscriber sub: subscribers) {
            sub.update(e);
        }
    }
}
