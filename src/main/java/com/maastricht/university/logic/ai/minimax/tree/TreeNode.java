package com.maastricht.university.logic.ai.minimax.tree;

import com.maastricht.university.logic.game.game.Move;
import org.nd4j.shade.guava.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreeNode<E extends Comparable<? super E>> implements ITreeNode<E> {

    private E element;
    private ITreeNode<E> parent;
    private List<ITreeNode<E>> children;
    private int depth;
    private int numberOfPlayers;
    public Integer score;
    private Move lastMove;
    private boolean childrenSorted;

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
        this.childrenSorted=false;
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

    @Override
    public List<ITreeNode<E>> getMaxChildren(int amount) {
        List<ITreeNode<E>> maxChildren = new ArrayList<ITreeNode<E>>();
        //TODO: complete this
        return maxChildren;
    }

    @Override
    public List<ITreeNode<E>> getMinChildren(int amount) {
        List<ITreeNode<E>> minChildren = new ArrayList<ITreeNode<E>>();
        //TODO: complete this
        return minChildren;
    }

    @Override
    public void subListChildren(int beginIndex, int endIndex) {
        //TODO: prevent indexOutOfBoundsExceptions
        this.children = this.children.subList(beginIndex, endIndex);
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

    public void sortChildren() {
        sortNodes(this.children);
    }

    private List<ITreeNode<E>> sortNodes(List<ITreeNode<E>> arr) {
        // Find the middle point
        if (arr.size() > 1) {
            int m = arr.size() / 2;

            List<List<ITreeNode<E>>> subSets = Lists.partition(arr, m);

            List<ITreeNode<E>> l1 = arr.subList(0, m);
            List<ITreeNode<E>> l2 = arr.subList(m, arr.size());

            l1 = sortNodes(l1);
            l2 = sortNodes(l2);

            return merge(l1, l2);
        }
        else {
            return arr;
        }
    }


    private List<ITreeNode<E>> merge(List<ITreeNode<E>> left, List<ITreeNode<E>> right) {
        // Initial indexes of first and second subArrays
        List<ITreeNode<E>> arr = new ArrayList<ITreeNode<E>>();

        int leftIndex = 0;
        int rightIndex = 0;

        //for every index in arr, add the element at the index of the left or right array, based on which one is smaller
        for(int i=0; i<arr.size(); i++) {
            if(left.get(leftIndex).getScore() < right.get(rightIndex).getScore()) {
                //arr.add(i, left.get(leftIndex));
                arr.add(left.get(leftIndex));
                if(leftIndex<left.size()-1)
                    leftIndex++;
                //if the left array is already fully in the arr array, copy the elements of the right array in arr (and stop the loop)
                else {
                    for(int j=i+1; j<arr.size(); j++) {
                        //arr.add(j, right.get(rightIndex));
                        arr.add(right.get(rightIndex));
                        rightIndex++;
                    }
                    i = arr.size();
                }
            }
            else {
                //arr.add(i, right.get(rightIndex));
                arr.add(right.get(rightIndex));
                if(rightIndex<right.size()-1) {
                    rightIndex++;
                }
                //if the right array is already fully in the arr array, copy the elements of the left array in arr (and stop the loop)
                else {
                    for(int j=i+1; j<arr.size(); j++) {
                        //arr.add(j, left.get(leftIndex));
                        arr.add(left.get(leftIndex));
                        leftIndex++;
                    }
                    i = arr.size();
                }
            }
        }
        return arr;
    }
}