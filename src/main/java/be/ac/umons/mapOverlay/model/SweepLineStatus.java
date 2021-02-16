package be.ac.umons.mapOverlay.model;

import be.ac.umons.sdd2.AVLTree;

public class SweepLineStatus<D extends Comparable<D>> extends AVLTree<D> {

    public SweepLineStatus(){
        super();
    }
    public SweepLineStatus(D d){
        this();
        this.setData(d);
        setHeight(1);
    }

    @Override
    public void insert(D d) {
        if (isEmpty())
            insertEmpty(d);
        else	{
            if (getHeight() == 1)
                insertLeaf(d);
            else if (getData().compareTo(d) > 0)
                getRight().insert(d);
            else
                getLeft().insert(d);
            equilibrate();
        }
    }

    public void insertLeaf(D d){
        if(this.getData().compareTo(d) > 0){
            this.setLeft(new SweepLineStatus<>(this.getData()));
            this.setRight(new SweepLineStatus<>(d));
        }
        else{
            this.setRight(new SweepLineStatus<>(this.getData()));
            this.setData(d);
            this.setLeft(new SweepLineStatus<>(d));
        }
    }

    // TODO : suppression

    @Override
    public String toString() {
        return  "SweepLineStatus{" +
                "Data : " + getData() +
                ", Height :" + getHeight() +
                "}";
    }
}
