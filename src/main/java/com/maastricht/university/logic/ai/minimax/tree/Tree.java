package com.maastricht.university.logic.ai.minimax.tree;

import com.maastricht.university.logic.game.game.Move;

public class Tree<E extends Comparable<? super E>> implements ITree<E> {

    private ITreeNode<E> root;
    private ITreeNode<E> addedRoot;
    private int numberOfPlayers;

    public Tree(int numberOfPlayers){
        root = new TreeNode<E>(null, null, numberOfPlayers, null);
        addedRoot = null;
        this.numberOfPlayers = numberOfPlayers;
    }

    public Tree(E element, int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        addedRoot = new TreeNode<E>(element, null, numberOfPlayers, null);
    }

    public ITreeNode<E> getRoot() {
        return this.addedRoot;
    }

    public void addRoot(E e, Move lastMove) {
        addedRoot = new TreeNode<E>(e, root, numberOfPlayers, lastMove);
    }

    public boolean hasRoot() {
        return addedRoot != null;
    }
}
