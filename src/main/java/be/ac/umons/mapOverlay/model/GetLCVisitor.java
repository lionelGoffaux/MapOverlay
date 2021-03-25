package be.ac.umons.mapOverlay.model;

import be.ac.umons.mapOverlay.model.geometry.Line;
import be.ac.umons.mapOverlay.model.geometry.Point;
import be.ac.umons.mapOverlay.model.geometry.Segment;

import java.util.ArrayList;

public class GetLCVisitor implements SweepLineStatusVisitor{
    private ArrayList<Segment> l = new ArrayList<>();
    private ArrayList<Segment> c = new ArrayList<>();

    @Override
    public void visitEmpty(SweepLineStatus empty) {

    }

    private final Point p;
    Line sl;

    public GetLCVisitor(Point p){
        this.p = p;
        sl = new Line(0, p.getY(), 1, p.getY());
    }

    @Override
    public void visitNode(SweepLineStatus node) {
        Segment s = node.getData();
        Point intersection = sl.getIntersection(s);
        if (intersection == null) intersection = s.getLowerPoint();
        int compare = intersection.compareX(p);

        if (compare == 0){
            node.getLeft().accept(this);
            node.getRight().accept(this);
        }
        else if (compare > 0) {
            node.getLeft().accept(this);
        }
        else {
            node.getRight().accept(this);
        }
    }

    @Override
    public void visitLeaf(SweepLineStatus leaf) {
        Segment s = leaf.getData();
        if(s.getLowerPoint().equals(p)) l.add(s);
        else if (s.contains(p)) c.add(s);
    }

    public ArrayList<Segment> getL() {
        return l;
    }

    public ArrayList<Segment> getC() {
        return c;
    }
}
