package be.ac.umons.mapOverlay.model.sweepLine;

import be.ac.umons.mapOverlay.model.geometry.Line;
import be.ac.umons.mapOverlay.model.geometry.Point;
import be.ac.umons.mapOverlay.model.geometry.Segment;

import java.util.ArrayList;

/**
 * Classe de construction d'ensemble de L et C.
 */
public class GetLCVisitor implements SweepLineStatusVisitor{
    private final ArrayList<Segment> l = new ArrayList<>();
    private final ArrayList<Segment> c = new ArrayList<>();

    private final Point p;
    final Line sl;

    public GetLCVisitor(Point p){
        this.p = p;
        sl = new Line(p.getX(), p.getY(), p.getX()+10, p.getY());
    }

    @Override
    public void visitEmpty(SweepLineStatus empty) {

    }

    @Override
    public void visitNode(SweepLineStatus node) {
        Segment s = node.getData();
        Point intersection = s.getIntersection(sl);
        if (intersection == null){
            intersection = s.getUpperPoint();
            if(!intersection.equals(p)){
                if(s.getLowerPoint().equals(p)) l.add(s);
                else if (s.contains(p)) c.add(s);
            }
        }
        int compare = intersection.compareX(p);

        if (compare == 0 ){
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

    /***
     * Retourne les segments de la sweep line se terminant par le point.
     * @return les segments de la sweep line se terminant par le point.
     */
    public ArrayList<Segment> getL() {
        return l;
    }

    /***
     * Retourne les segments de la sweep line passant par le point.
     * @return les segments de la sweep line passant par le point.
     */
    public ArrayList<Segment> getC() {
        return c;
    }
}
