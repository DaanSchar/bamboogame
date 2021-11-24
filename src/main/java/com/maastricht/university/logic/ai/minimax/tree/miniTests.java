package com.maastricht.university.logic.ai.minimax.tree;

import com.maastricht.university.logic.ai.agent.Agent;
import com.maastricht.university.logic.ai.agent.AlphaBetaAgent;
import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.ai.minimax.tree.ITree;
import com.maastricht.university.logic.ai.minimax.tree.Tree;

public class miniTests {

    public static void main(String[] args) {

        GameState gameState = new GameState(1, 2);

        ITreeNode node = new TreeNode(gameState.clone(), null, 2, null);
        node.setScore(5);
        System.out.println(node.hasScore());
        System.out.println(node.getScore());
        System.out.println(((TreeNode<?>) node).score);
        System.out.println();
        System.out.println();

        AlphaBetaAgent agent2 = new AlphaBetaAgent(gameState, 2, 3);
        Agent agent1 = new Agent(gameState, 1);

        while(!gameState.isGameOver()) {
            agent1.move();
            if(!gameState.isGameOver())
                agent2.move();
            System.out.println(gameState.isGameOver());
        }
        System.out.println(gameState.winner());


//        CreateTree createTree = new CreateTree(gameState);
//        ITree tree = createTree.getTree();
//        ITreeNode root = tree.getRoot();
//        System.out.println(root.getDepth());
    }
}
