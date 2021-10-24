package com.maastricht.university.minimax.tree;

/**
 * Tree Data structure.
 * Always has a null node root at the start which has the
 * actual root as a child.
 */
public interface ITree<E> {

    /**
     * @return root node of the tree
     */
    public ITreeNode<E> getRoot();

    /**
     * Sets the root of the tree.
     * This will overwrite the current root if the tree
     * already has a root.
     */
    public void addRoot(E e);

    /**
     * @return boolean stating if tree possesses a root
     */
    public boolean hasRoot();
}
