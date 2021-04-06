package be.ac.umons.mapOverlay.model.sweepLine;

public interface SweepLineStatusVisitor {
    /***
     * Méthode appelée lors de la visite d'un noeud vide.
     * @param empty
     */
    void visitEmpty(SweepLineStatus empty);

    /***
     * Méthode appelée lors de la visite d'un noeud interne.
     * @param node
     */
    void visitNode(SweepLineStatus node);

    /***
     * Méthode appelée lors de la visite d'une feuille.
     * @param leaf
     */
    void visitLeaf(SweepLineStatus leaf);
}
