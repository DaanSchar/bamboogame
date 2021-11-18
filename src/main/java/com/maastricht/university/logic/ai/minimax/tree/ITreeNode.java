package com.maastricht.university.logic.ai.minimax.tree;

import com.maastricht.university.logic.game.game.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * Node for tree Data structure.
 * there are no restrictions on how many children
 * each node can have.
 */
public interface ITreeNode<E> {

    /**
     *
     * @return whether the last move made is stored
     */
    boolean hasLastMove();

    /**
     *
     * @return last move done
     */
    Move getLastMove();

    /**
     * @return score of a player of node in tree stored by this node.
     */
    Integer getScore();

    /**
     *
     * @return whether the score is stored yet or not
     */
    boolean hasScore();

    /**
     *
     * @param score the score of the player
     */
    void setScore(Integer score);

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
     * returns the first child in the list
     * @return
     */
    ITreeNode<E> getLeftChild();

    /**
     * this returns the leaf node of a branch
     * @return one node
     */
    ArrayList<ITreeNode> getLeafNode();

    /**
     * adds a child to node to this node.
     * @param element
     */
    void addChild(E element, Move lastMove);

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
