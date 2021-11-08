package com.maastricht.university.logic.ai.minimax.tree;

import com.maastricht.university.logic.game.util.interfaces.IScoreSystem;

//TODO: either keep it like this, where E needs to implement IScoreSystem and loses generality
// Or make it not implement it, and therefor make the user of the program keep adding the score itself before it can use it, keep generality, but less automization
// Those are the two options
public class Tree<E extends Comparable/*IScoreSystem*/> implements ITree<E> {

    private ITreeNode<E> root;
    private ITreeNode<E> addedRoot;
    private int numberOfPlayers;

    public Tree(int numberOfPlayers){
        root = new TreeNode<E>(null, null, numberOfPlayers);
        addedRoot = null;
        this.numberOfPlayers = numberOfPlayers;
    }

    public Tree(E element, int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        addedRoot = new TreeNode<E>(element, null, numberOfPlayers);
    }

    public ITreeNode<E> getRoot() {
        return this.addedRoot;
    }

    public void addRoot(E e) {
        addedRoot = new TreeNode<E>(e, root, numberOfPlayers);
    }

    public boolean hasRoot() {
        return addedRoot != null;
    }
}
