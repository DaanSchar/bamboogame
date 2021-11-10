package com.maastricht.university.logic.ai.minimax.tree;

import java.util.List;

/**
 * Node for tree Data structure.
 * there are no restrictions on how many children
 * each node can have.
 */
public interface ITreeNode<E> {

    /**
     * @return score of a player of node in tree stored by this node.
     */
    int getScore(int playerColour);

    /**
     *
     * @param playerColour the player whose score to set
     * @param score the score of the player
     */
    void setScore(int playerColour, int score);

    /**
     * @return depth of node in tree stored by this node.
     */
    int getDepth();

    /**
     * @return Element stored by this node.
     */
    E getElement();

    /**
     * set the element which will be stored by this node
     * @param element
     */
    void setElement(E element);

    /**
     * a parent is the predecessing node of this node.
     * that means that the parent node stored this node as a child.
     *
     * a parent can have many children.
     * a child has 1 parent.
     *
     * @return parent of this node.
     */
    ITreeNode<E> getParent();

    /**
     * a node can have many children nodes.
     * @return list of children nodes
     */
    List<ITreeNode<E>> getChildren();

    /**
     * adds a child to node to this node.
     * @param element
     */
    void addChild(E element);

    /**
     * compares all children to each other using the
     * comparable interface.
     *
     * @return child with the largest element
     */
    ITreeNode<E> getMaxChild();

    /**
     * compares all children to each other using the
     * comparable interface.
     *
     * @return child with the smallest element
     */
    ITreeNode<E> getMinChild();

    /**
     * @return boolean regarding if this node contains any children
     */
    boolean hasChildren();

    /**
     * deletes this node.
     * node must have no children.
     */
    void delete();
}
