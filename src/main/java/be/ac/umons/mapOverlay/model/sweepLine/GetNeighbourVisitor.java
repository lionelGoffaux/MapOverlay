package be.ac.umons.mapOverlay.model.sweepLine;

import be.ac.umons.mapOverlay.model.geometry.Line;
import be.ac.umons.mapOverlay.model.geometry.Point;
import be.ac.umons.mapOverlay.model.geometry.Segment;
import be.ac.umons.mapOverlay.model.intersectionsFinder.IntersectionsFinder;

public abstract class GetNeighbourVisitor implements SweepLineStatusVisitor{

    protected Point p;
    protected final Line sl = IntersectionsFinder.getInstance().getSweepLine();;
    protected Segment neighbour = null;

    public GetNeighbourVisitor(Point p){
        this.p = p;
    }

    public GetNeighbourVisitor(Segment s){
        //System.out.println("s = " + s);
        p = sl.getIntersection(s);
        //System.out.println("p = " + p);
        if (p==null) p = s.getUpperPoint();
        //System.out.println("p = " + p);
    }

    @Override
    public void visitEmpty(SweepLineStatus empty) {

    }

    public Segment getNeighbour() {
        return neighbour;
    }
}