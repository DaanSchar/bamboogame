package com.maastricht.university.logic.ai.minimax.tree;

import com.maastricht.university.logic.game.util.interfaces.IScoreSystem;

public class Tree<E extends Comparable> implements ITree<E> {

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
