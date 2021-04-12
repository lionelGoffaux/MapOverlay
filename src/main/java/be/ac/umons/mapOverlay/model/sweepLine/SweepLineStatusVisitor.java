package be.ac.umons.mapOverlay.model.sweepLine;

/**
 * Interface visiteur de sweep line.
 */
public interface SweepLineStatusVisitor {
    /***
     * Méthode appelée lors de la visite d'un nœud vide.
     * @param empty un nœud vide.
     */
    void visitEmpty(SweepLineStatus empty);

    /***
     * Méthode appelée lors de la visite d'un nœud interne.
     * @param node un nœd interne.
     */
    void visitNode(SweepLineStatus node);

    /***
     * Méthode appelée lors de la visite d'une feuille.
     * @param leaf un nœud feuille.
     */
    void visitLeaf(SweepLineStatus leaf);
}
