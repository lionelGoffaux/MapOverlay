package be.ac.umons.mapOverlay.model;

import be.ac.umons.mapOverlay.model.geometry.Point;
import be.ac.umons.mapOverlay.model.geometry.Segment;

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
        if(p==null || (intersection != null && p.compareX(intersection) > 0 )) {
            neighbour = curr;
            node.getRight().accept(this);
        }
        else node.getLeft().accept(this);
    }

    @Override
    public void visitLeaf(SweepLineStatus leaf) {
        Segment curr = leaf.getData();
        Point intersection = sl.getIntersection(curr);

        if(p==null || (intersection != null && p.compareX(intersection) > 0 )) neighbour = curr;
    }
}
