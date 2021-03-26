package be.ac.umons.mapOverlay.model;

import be.ac.umons.mapOverlay.model.geometry.Line;
import be.ac.umons.mapOverlay.model.intersectionsFinder.IntersectionsFinder;
import be.ac.umons.mapOverlay.model.geometry.Point;
import be.ac.umons.mapOverlay.model.geometry.Segment;
import be.ac.umons.sdd2.AVLTree;

import java.util.ArrayList;

public class SweepLineStatus extends AVLTree<Segment> {

    public SweepLineStatus(){
        super();
    }
    public SweepLineStatus(Segment d, SweepLineStatus l, SweepLineStatus r){
        this();
        setData(d);
        setLeft(l);
        setRight(r);
        setHeight(1);
    }

    @Override
    public void insert(Segment d) {
        if (isEmpty())
            insertEmpty(d);
        else if (getHeight() == 1)
            insertLeaf(d);
        else if (getData().compareTo(d) < 0)
            getRight().insert(d);
        else
            getLeft().insert(d);
        equilibrate();
    }

    public void insertAll(ArrayList<Segment> segments){
        for(Segment s: segments){
            insert(s);
        }
    }

    public void insertLeaf(Segment d){
        if(this.getData().compareTo(d) < 0){
            this.setLeft(new SweepLineStatus(this.getData(), new SweepLineStatus(), new SweepLineStatus()));
            this.setRight(new SweepLineStatus(d, new SweepLineStatus(), new SweepLineStatus()));
        }
        else{
            this.setRight(new SweepLineStatus(this.getData(), new SweepLineStatus(), new SweepLineStatus()));
            this.setData(d);
            this.setLeft(new SweepLineStatus(d, new SweepLineStatus(), new SweepLineStatus()));
        }
    }

    @Override
    public void insertEmpty(Segment d) {
        setData(d);
        setRight(new SweepLineStatus());
        setLeft(new SweepLineStatus());
        setHeight(1);
    }

    public boolean isLeaf(){
        return getLeft().isEmpty() && getRight().isEmpty();
    }

    @Override
    public void suppress(Segment d){
        if (!isEmpty() && !(getHeight()==1 && !getData().equals(d))){
            if (getData().equals(d) && getHeight()==1){
                setData(null);
                setLeft(null);
                setRight(null);
            } else if (getLeft().getData().equals(d) && getLeft().isLeaf()) {
                setData(getRight().getData());
                setLeft(getRight().getLeft());
                setRight(getRight().getRight());
            } else if (getRight().getData().equals(d) && getRight().isLeaf()) {
                setData(getLeft().getData());
                setRight(getLeft().getRight());
                setLeft(getLeft().getLeft());
            } else if (d.compareTo(getData()) < 0) {
                getLeft().suppress(d);
            } else if (d.compareTo(getData()) > 0) {
                getRight().suppress(d);
            }
            if (getData()!=null && getData().equals(d))
                setData(getLeft().searchMax());
            equilibrate();
        }
    }

    @Override
    public void suppressRoot(){
        if (balance()==0){
            setLeft(new SweepLineStatus());
            setRight(new SweepLineStatus());
        }
    }


    public void suppressAll(ArrayList<Segment> segments){
        for (Segment s: segments) {
            suppress(s);
        }
    }

    public void accept(SweepLineStatusVisitor visitor){
        if(isEmpty()) visitor.visitEmpty(this);
        else if(isLeaf()) visitor.visitLeaf(this);
        else visitor.visitNode(this);
    }

    @Override
    public SweepLineStatus getLeft() {
        return (SweepLineStatus) super.getLeft();
    }

    @Override
    public SweepLineStatus getRight() {
        return (SweepLineStatus) super.getRight();
    }

    @Override
    public String toString() {
        return  "SweepLineStatus{" +
                "Data : " + getData() +
                ", Height : " + getHeight() +
                "}";
    }

    @Deprecated //TODO: remove
    public Segment getLeftNeighbour(Point point) {
        return getLeftNeighbour(point, null);
    }

    @Deprecated //TODO: remove
    private Segment getLeftNeighbour(Point point, Segment segment){
        Line s = new Line(0, point.getY(), 1, point.getY());
        Point p = s.getIntersection(getData());
        if(p==null || point.compareTo(p) <= 0) { // Le segment est à droite du point TODO: vérifier
            if( isLeaf()){
                return segment;
            }
            return getLeft().getLeftNeighbour(point, segment);
        } else { // Le segment est à gauche du point
            if(isLeaf()){
                return getData();
            }
            return getRight().getLeftNeighbour(point, getData());
        }
    }

    @Deprecated //TODO: remove
    public Segment getRightNeighbour(Point point) {
        return getRightNeighbour(point, null);
    }

    @Deprecated //TODO: remove
    private Segment getRightNeighbour(Point point, Segment segment){
        Line s = new Line(0, point.getY(), 1, point.getY());
        Point p = s.getIntersection(getData());
        if(p==null || point.compareTo(p) < 0) { // Le segment est à droite du point TODO: vérifier
            if( isLeaf()){
                return getData();
            }
            return getLeft().getRightNeighbour(point, getData());
        } else { // Le segment est à gauche du point
            if(isLeaf()){
                return segment;
            }
            return getRight().getRightNeighbour(point, segment);
        }
    }

    @Deprecated //TODO: remove
    public Segment getLeftNeighbour(Segment seg) {
        Line s = IntersectionsFinder.getInstance().getSweepLine();
        Point p = s.getIntersection(seg);
        if(p==null) return null;
        return getLeftNeighbour(p, null);
    }

    @Deprecated //TODO: remove
    public Segment getRightNeighbour(Segment seg) {  // TODO: vérifier
        Line s = IntersectionsFinder.getInstance().getSweepLine();
        Point p = s.getIntersection(seg);
        if(p==null) return null;
        return getRightNeighbour(p, null);
    }

}
