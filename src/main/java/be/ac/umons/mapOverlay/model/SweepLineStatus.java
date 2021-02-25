package be.ac.umons.mapOverlay.model;

import be.ac.umons.sdd2.AVLTree;

public class SweepLineStatus<D extends Comparable<D>> extends AVLTree<D> {

    public SweepLineStatus(){
        super();
    }
    public SweepLineStatus(D d, SweepLineStatus<D> l, SweepLineStatus<D> r){
        this();
        setData(d);
        setLeft(l);
        setRight(r);
        setHeight(1);
    }

    @Override
    public void insert(D d) {
        if (isEmpty())
            insertEmpty(d);
        else {
            if (getHeight() == 1)
                insertLeaf(d);
            else if (getData().compareTo(d) < 0)
                getRight().insert(d);
            else
                getLeft().insert(d);
            equilibrate();
        }
    }

    public void insertLeaf(D d){
        if(this.getData().compareTo(d) < 0){
            this.setLeft(new SweepLineStatus<>(this.getData(), new SweepLineStatus<D>(), new SweepLineStatus<D>()));
            this.setRight(new SweepLineStatus<>(d, new SweepLineStatus<D>(), new SweepLineStatus<D>()));
        }
        else{
            this.setRight(new SweepLineStatus<>(this.getData(), new SweepLineStatus<D>(), new SweepLineStatus<D>()));
            this.setData(d);
            this.setLeft(new SweepLineStatus<>(d, new SweepLineStatus<D>(), new SweepLineStatus<D>()));
        }
    }

    @Override
    public void insertEmpty(D d) {
        setData(d);
        setRight(new SweepLineStatus<>());
        setLeft(new SweepLineStatus<>());
        setHeight(1);
    }

    public boolean isLeaf(){
        return getLeft().isEmpty() && getRight().isEmpty();
    }

    @Override
    public void suppress(D d){
        // TODO : suprresion noeud unique
        if (!isEmpty()){
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
            setLeft(new SweepLineStatus<>());
            setRight(new SweepLineStatus<>());
        }
    }

    @Override
    public SweepLineStatus<D> getLeft() {
        return (SweepLineStatus<D>) super.getLeft();
    }

    @Override
    public SweepLineStatus<D> getRight() {
        return (SweepLineStatus<D>) super.getRight();
    }

    @Override
    public String toString() {
        return  "SweepLineStatus{" +
                "Data : " + getData() +
                ", Height : " + getHeight() +
                "}";
    }
}
