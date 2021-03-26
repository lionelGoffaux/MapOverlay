package be.ac.umons.mapOverlay.model.sweepLine;

public interface SweepLineStatusVisitor {
    public void visitEmpty(SweepLineStatus empty);
    public void visitNode(SweepLineStatus node);
    public void visitLeaf(SweepLineStatus leaf);
}
