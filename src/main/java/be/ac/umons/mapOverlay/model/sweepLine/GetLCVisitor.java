package be.ac.umons.mapOverlay.model.sweepLine;

import be.ac.umons.mapOverlay.model.geometry.Line;
import be.ac.umons.mapOverlay.model.geometry.Point;
import be.ac.umons.mapOverlay.model.geometry.Segment;

import java.util.ArrayList;

public class GetLCVisitor implements SweepLineStatusVisitor{
    private ArrayList<Segment> l = new ArrayList<>();
    private ArrayList<Segment> c = new ArrayList<>();

    private final Point p;
    Line sl;

    @Override
    public void visitEmpty(SweepLineStatus empty) {

    }

    public GetLCVisitor(Point p){
        this.p = p;
        sl = new Line(0, p.getY(), 1, p.getY());
        //System.out.println("p = " + p);
        //System.out.println("sl = " + sl);
    }

    @Override
    public void visitNode(SweepLineStatus node) {
        //System.out.println("===============NODE================");
        Segment s = node.getData();
        //System.out.println("s = " + s);
        Point intersection = sl.getIntersection(s);
        if (intersection == null){
            intersection = s.getUpperPoint();
            if(!intersection.equals(p)){
                if(s.getLowerPoint().equals(p)) l.add(s);
                else if (s.contains(p)) c.add(s);
            }
        }
        int compare = intersection.compareX(p);
        //System.out.println("compare = " + compare);

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
        //System.out.println("===============NODE================");
    }

    @Override
    public void visitLeaf(SweepLineStatus leaf) {
        //System.out.println("===============LEAF================");
        Segment s = leaf.getData();
        //System.out.println("s = " + s);
        if(s.getLowerPoint().equals(p)) l.add(s);
        else if (s.contains(p)) c.add(s);
        //System.out.println("===============LEAF================");
    }

    public ArrayList<Segment> getL() {
        return l;
    }

    public ArrayList<Segment> getC() {
        return c;
    }
}
