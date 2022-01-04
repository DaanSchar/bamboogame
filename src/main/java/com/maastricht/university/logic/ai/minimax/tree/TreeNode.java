package com.maastricht.university.logic.ai.minimax.tree;

import com.maastricht.university.logic.game.game.Move;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<E extends Comparable<? super E>> implements ITreeNode<E> {

    private E element;
    private ITreeNode<E> parent;
    private ArrayList<ITreeNode<E>> children;
    private int depth;
    private int numberOfPlayers;
    public Integer score;
    private Move lastMove;

    public TreeNode(E element, ITreeNode<E> parent, int numberOfPlayers, Move lastMove) {
        this.element = element;
        this.parent = parent;
        this.children = new ArrayList<>();
        if(parent != null)
            this.depth = parent.getDepth() + 1;
        else
            this.depth = 1;
        this.numberOfPlayers = numberOfPlayers;
        this.lastMove = lastMove;
    }

    public boolean hasLastMove() {
        if(lastMove == null)
            return false;
        return true;
    }

    public Move getLastMove() { return lastMove; }

    public void setScore(Integer score) {
        this.score = score;
    }

    public boolean hasScore() {
        if(this.score == null)
            return false;
        return true;
    }

    public Integer getScore() {
        if(this.score == null)
            if(depth%2 == 1) //if odd depth, so parent is Minimizer
                return Integer.MAX_VALUE - 1;
            else
                return Integer.MIN_VALUE + 1;
        return this.score;
    }

    public int getDepth() {return this.depth;}

    public E getElement() {
        return this.element;
    }

    public void setElement(E e) {
        this.element = e;
    }

    public ITreeNode<E> getParent() {
        return this.parent;
    }

    public List<ITreeNode<E>> getChildren() {
        return this.children;
    }

    public void addChild(E e, Move lastMove) {
        children.add(new TreeNode(e, this, this.numberOfPlayers, lastMove));
    }

    public void removeChild(int index) { children.remove(index); }

    @Override
    public void clearChildren() { children.clear(); }

    public ITreeNode<E> getMaxChild() {
        ITreeNode<E> maxChild = children.get(0);

        for (ITreeNode<E> child : children)
            if (compare(child, maxChild) == 1)
                maxChild = child;

        return maxChild;
    }

    public ITreeNode<E> getMinChild() {
        ITreeNode<E> minChild = children.get(0);

        for (ITreeNode<E> child : children)
            if (compare(child, minChild) == -1)
                minChild = child;

        return minChild;
    }

    public boolean hasChildren() {
        return children.size() > 0;
    }

    public void delete() {
        if (this.hasChildren())
            return;

        this.parent = null;
        this.element = null;
        this.children = new ArrayList<>();
    }

    /**
     * returns 1 if a > b.
     * return 0 if a == b.
     * returns -1 if a < b.
     */
    private int compare(ITreeNode<E> a, ITreeNode<E> b) {
        if(!a.hasScore() || !b.hasScore()) {
            return a.getElement().compareTo(b.getElement());
        }
        if (a.getScore() > b.getScore())
            return 1;
        else if (a.getScore() == b.getScore())
            return 0;
        else
            return -1;
    }
}