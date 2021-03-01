package be.ac.umons.mapOverlay.model;

import be.ac.umons.mapOverlay.model.map.Point;
import be.ac.umons.mapOverlay.model.map.Segment;
import be.ac.umons.sdd2.AVLTree;

import java.util.ArrayList;

public class SweepLineStatus<D extends Comparable<D>> extends AVLTree<D> {

    public SweepLineStatus(){
        super();
    }
    public SweepLineStatus(D d){
        this();
        this.setData(d);
        setHeight(1);
    }

    //TODO: (idée) égalité -> comparer x lowerPoint
    @Override
    public void insert(D d) {
        if (isEmpty())
            insertEmpty(d);
        else	{
            if (getHeight() == 1)
                insertLeaf(d);
            else if (getData().compareTo(d) < 0)
                getRight().insert(d);
            else
                getLeft().insert(d);
            equilibrate();
        }
    }

    public void insertAll(ArrayList<D> segments){
        for(D s: segments){
            insert(s);
        }
    }

    public void insertLeaf(D d){
        if(this.getData().compareTo(d) < 0){
            this.setLeft(new SweepLineStatus<>(this.getData()));
            this.setRight(new SweepLineStatus<>(d));
        }
        else{
            this.setRight(new SweepLineStatus<>(this.getData()));
            this.setData(d);
            this.setLeft(new SweepLineStatus<>(d));
        }
    }

    @Override
    public void suppress(D d) {
        //TODO
    }

    public void suppressAll(ArrayList<D> segments){
        for (D s: segments) {
            suppress(s);
        }
    }

    @Override
    public String toString() {
        return  "SweepLineStatus{" +
                "Data : " + getData() +
                ", Height :" + getHeight() +
                "}";
    }

    public ArrayList<D>  getL() {
        //TODO
        return null;
    }

    public ArrayList<D>  getC() {
        //TODO
        return null;
    }

    public D getLeftNeighbour(Point point) {
        //TODO
        return null;
    }

    public D getRightNeighbour(Point point) {
        //TODO
        return null;
    }

    public D getLeftNeighbour(D sp) {
        //TODO
        return null;
    }
}
