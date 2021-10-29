package com.maastricht.university.minimax.tree;

public class Tree<E extends Comparable<E>> implements ITree<E> {

    private ITreeNode<E> root = new TreeNode<>(null, null);
    private ITreeNode<E> addedRoot;


    public Tree(){
        addedRoot = null;
    }

    public Tree(E element) {
        addedRoot = new TreeNode<E>(element, null);
    }

    public ITreeNode<E> getRoot() {
        return this.addedRoot;
    }

    public void addRoot(E e) {
        addedRoot = new TreeNode<E>(e, root);
    }

    public boolean hasRoot() {
        return addedRoot != null;
    }
}
