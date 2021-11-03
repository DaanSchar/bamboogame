package com.maastricht.university.minimax.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<E extends Comparable<E>> implements ITreeNode<E> {

    private E element;
    private ITreeNode<E> parent;
    private ArrayList<ITreeNode<E>> children;
    private int depth;

    public TreeNode(E element, ITreeNode<E> parent) {
        this.element = element;
        this.parent = parent;
        this.children = new ArrayList<>();
        if(parent != null)
            this.depth = parent.getDepth() + 1;
        else
            this.depth = 1;
    }

    public int getDepth() {return depth;}

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

    public void addChild(E e) {
        children.add(new TreeNode(e, this));
    }

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
        return a.getElement().compareTo(b.getElement());
    }

}