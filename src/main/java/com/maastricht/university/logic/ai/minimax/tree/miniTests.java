package com.maastricht.university.logic.ai.minimax.tree;

import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.ai.minimax.tree.ITree;
import com.maastricht.university.logic.ai.minimax.tree.Tree;

public class miniTests {

    public static void main(String[] args) {

        GameState gameState = new GameState(1, 2);
        CreateTree createTree = new CreateTree(gameState);
        ITree tree = createTree.getTree();
        ITreeNode root = tree.getRoot();
        System.out.println(root.getDepth());
    }
}
