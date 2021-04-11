package be.ac.umons.mapOverlay.model.sweepLine;

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

    /**
     * Insert une liste de segments dans la SweepLineStatus.
     * @param segments
     */
    public void insertAll(ArrayList<Segment> segments){
        for(Segment s: segments){
            insert(s);
        }
    }

    private void insertLeaf(Segment d){
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

    protected boolean isLeaf(){
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
            } else if (d.compareTo(getData()) <= 0) {
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

    /**
     * Supprime de la SweepLineStatus tous les segments de la liste donnée en paramètre
     * @param segments
     */
    public void suppressAll(ArrayList<Segment> segments){
        for (Segment s: segments) {
            suppress(s);
        }
    }

    /***
     * Accepte un visiteur.
     * @param visitor
     */
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

}
