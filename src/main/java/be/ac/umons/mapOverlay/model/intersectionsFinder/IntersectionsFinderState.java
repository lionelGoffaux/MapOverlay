package be.ac.umons.mapOverlay.model.intersectionsFinder;

import be.ac.umons.mapOverlay.model.map.Map;

public interface IntersectionsFinderState {

    /**
     * Méthode appelée lors de l'entrée dans un état.
     * @param context
     */
    void entry(IntersectionsFinder context);

    /**
     * Méthode appelée lorsqu'on appuie sur le bouton step.
     * @param context
     */
    void stepForward(IntersectionsFinder context);

    /**
     * Méthode appelée lorsqu'on appuie sur le bouton find all.
     * @param context
     */
    void findAll(IntersectionsFinder context);

    /**
     * Modifie la carte.
     * @param context
     * @param map
     */
    void setMap(IntersectionsFinder context, Map map);

    /**
     * Méthode appelée lors de la création d'un nouveau segment avec la souris.
     * @param context
     * @param x
     * @param y
     */
    void startNewSegment(IntersectionsFinder context, double x, double y);

    /**
     * Méthode appelée lorsque la souris est relâchée, signifie la fin d'un segment.
     * @param context
     * @param x
     * @param y
     */
    void endNewSegment(IntersectionsFinder context, double x, double y);

    /**
     * Méthode appelée lors de la création d'une nouvelle map.
     * @param context
     */
    void newMap(IntersectionsFinder context);
}
