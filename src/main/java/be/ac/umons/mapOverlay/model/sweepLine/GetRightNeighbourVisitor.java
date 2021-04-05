package be.ac.umons.mapOverlay.model.sweepLine;

import be.ac.umons.mapOverlay.model.geometry.Point;
import be.ac.umons.mapOverlay.model.geometry.Segment;

public class GetRightNeighbourVisitor extends GetNeighbourVisitor{
    public GetRightNeighbourVisitor(Point p) {
        super(p);
    }

    public GetRightNeighbourVisitor(Segment s) {
        super(s);
    }

    @Override
    public void visitNode(SweepLineStatus node) {
        Segment curr = node.getData();
        Point intersection = sl.getIntersection(curr);
        if (intersection == null) intersection = curr.getUpperPoint();

        if(p.compareX(intersection) < 0){
            neighbour = curr;
            node.getLeft().accept(this);
        }
        else node.getRight().accept(this);
    }

    @Override
    public void visitLeaf(SweepLineStatus leaf) {
        Segment curr = leaf.getData();
        Point intersection = sl.getIntersection(curr);
        if(intersection == null) intersection = curr.getUpperPoint();

        if(p.compareX(intersection) < 0) neighbour = curr;
    }
}
