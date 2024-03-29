package be.ac.umons.mapOverlay.model.sweepLine;

import be.ac.umons.mapOverlay.model.geometry.Line;
import be.ac.umons.mapOverlay.model.geometry.Point;
import be.ac.umons.mapOverlay.model.geometry.Segment;
import be.ac.umons.mapOverlay.model.intersectionsFinder.IntersectionsFinder;

/**
 * Classe visiteur permettant d'obtenir les voisins d'un segment ou d'un point.
 */
public abstract class GetNeighbourVisitor implements SweepLineStatusVisitor{

    protected Point p;
    protected final Line sl = IntersectionsFinder.getInstance().getSweepLine();
    protected Segment neighbour = null;

    public GetNeighbourVisitor(Point p){
        this.p = p;
    }

    public GetNeighbourVisitor(Segment s){
        p = sl.getIntersection(s);
        if (p==null) p = IntersectionsFinder.getInstance().getEventPoint();
    }

    @Override
    public void visitEmpty(SweepLineStatus empty) {

    }

    /***
     * Retourne le voisin du point ou segment dans la sweep line.
     * @return le segment voisin.
     */
    public Segment getNeighbour() {
        return neighbour;
    }
}
