package be.ac.umons.mapOverlay.model.sweepLine;

import be.ac.umons.mapOverlay.model.geometry.Point;
import be.ac.umons.mapOverlay.model.geometry.Segment;

/**
 * Classe visiteur permettant d'obtenir le voisin gauche d'un segment ou d'un point dans la SweepLineStatus.
 */
public class GetLeftNeighbourVisitor extends GetNeighbourVisitor{

    public GetLeftNeighbourVisitor(Point p) {
        super(p);
    }

    public GetLeftNeighbourVisitor(Segment s) {
        super(s);
    }

    @Override
    public void visitNode(SweepLineStatus node) {
        Segment curr = node.getData();
        Point intersection = sl.getIntersection(curr);
        if(intersection != null && p.compareX(intersection) > 0 ) {
            neighbour = curr;
            node.getRight().accept(this);
        }
        else node.getLeft().accept(this);
    }

    @Override
    public void visitLeaf(SweepLineStatus leaf) {
        Segment curr = leaf.getData();
        Point intersection = sl.getIntersection(curr);

        if(intersection != null && p.compareX(intersection) > 0 ) neighbour = curr;
    }
}
