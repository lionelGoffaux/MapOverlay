package be.ac.umons.mapOverlay.model.intersectionsFinder;

import be.ac.umons.mapOverlay.model.map.Map;

/**
 * Interface d'état.
 */
public interface IntersectionsFinderState {

    /**
     * Méthode appelée lors de l'entrée dans un état.
     * @param context l'IntersectionsFinder.
     */
    void entry(IntersectionsFinder context);

    /**
     * Méthode appelée lorsqu'on appuie sur le bouton step.
     * @param context l'IntersectionsFinder.
     */
    void stepForward(IntersectionsFinder context);

    /**
     * Méthode appelée lorsqu'on appuie sur le bouton find all.
     * @param context l'IntersectionsFinder.
     */
    void findAll(IntersectionsFinder context);

    /**
     * Modifie la carte.
     * @param context l'IntersectionsFinder.
     * @param map la carte.
     */
    void setMap(IntersectionsFinder context, Map map);

    /**
     * Méthode appelée lors de la création d'un nouveau segment avec la souris.
     * @param context l'IntersectionsFinder.
     * @param x coordonnée en x.
     * @param y coordonnée en y.
     */
    void startNewSegment(IntersectionsFinder context, double x, double y);

    /**
     * Méthode appelée lorsque la souris est relâchée, signifie la fin d'un segment.
     * @param context l'IntersectionsFinder.
     * @param x coordonnée en x.
     * @param y coordonnée en y.
     */
    void endNewSegment(IntersectionsFinder context, double x, double y);

    /**
     * Méthode appelée lors de la création d'une nouvelle map.
     * @param context l'IntersectionsFinder.
     */
    void newMap(IntersectionsFinder context);
}
