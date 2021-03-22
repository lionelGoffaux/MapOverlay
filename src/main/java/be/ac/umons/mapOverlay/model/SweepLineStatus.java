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

    public ArrayList<Segment> getL(Point point) { // TODO: better solution?
        ArrayList<Segment> res = new ArrayList<>();
        for (Segment seg : getLeaves()){
            if(seg.getLowerPoint().equals(point)){
                res.add(seg);
            }
        }
        return res;
    }

    public ArrayList<Segment> getC(Point point) { // TODO: better solution?
        ArrayList<Segment> res = new ArrayList<>();
        for (Segment seg : getLeaves()){
            if(seg.contains(point)){
                res.add(seg);
            }
        }
        return res;
    }

    public Segment getLeftNeighbour(Point point) {
        return getLeftNeighbour(point, null);
    }

    private Segment getLeftNeighbour(Point point, Segment segment){
        Line s = new Line(0, point.getY(), 1, point.getY());
        Point p = s.getIntersection(getData());
        if(point.compareTo(p) < 0) { // Le segment est à droite du point TODO: vérifier
            System.out.println("droite");
            if( isLeaf()){
                System.out.println("feuille");
                System.out.println("segment = " + segment);
                return segment;
            }
            return getLeft().getLeftNeighbour(point, segment);
        } else { // Le segment est à gauche du point
            System.out.println("gauche");
            if(isLeaf()){
                System.out.println("feuille");
                System.out.println("getData() = " + getData());
                return getData();
            }
            return getRight().getLeftNeighbour(point, getData());
        }
    }

    public Segment getRightNeighbour(Point point) {
        return getRightNeighbour(point, null);
    }

    private Segment getRightNeighbour(Point point, Segment segment){
        Line s = new Line(0, point.getY(), 1, point.getY());
        Point p = s.getIntersection(getData());
        if(point.compareTo(p) < 0) { // Le segment est à droite du point TODO: vérifier
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

    public Segment getLeftNeighbour(Segment seg) {
        Line s = IntersectionsFinder.getInstance().getSweepLine();
        Point p = s.getIntersection(seg);
        return getLeftNeighbour(p, null);
    }

    public Segment getRightNeighbour(Segment seg) {
        Line s = IntersectionsFinder.getInstance().getSweepLine();
        Point p = s.getIntersection(seg);
        return getRightNeighbour(p, null);
    }

    public ArrayList<Segment> getLeaves() {
        ArrayList<Segment> list = new ArrayList<>();
        if(isEmpty()){
            return list;
        } else if(isLeaf()){
            list.add(getData());
        } else{
            list.addAll(getLeft().getLeaves());
            list.addAll(getRight().getLeaves());
        }
        return list;
    }

}
