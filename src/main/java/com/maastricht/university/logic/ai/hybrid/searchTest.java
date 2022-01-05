package com.maastricht.university.logic.ai.hybrid;

import com.maastricht.university.logic.ai.agent.Agent;
import com.maastricht.university.logic.ai.agent.AlphaBetaAgent;
import com.maastricht.university.logic.ai.minimax.tree.TreeNode;
import com.maastricht.university.logic.game.game.Evaluation1;
import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.ai.minimax.tree.ITree;
import com.maastricht.university.logic.ai.minimax.tree.Tree;
import com.maastricht.university.logic.game.util.interfaces.IEvaluationFunction;

public class searchTest {

    public static void main(String[] args) {
        GameState gameState = new GameState(2, 2);

        TreeNode node = new TreeNode(gameState, null, 2, null);
        node.setScore(14);
        System.out.println(node.hasScore());
        System.out.println(node.getScore());
        System.out.println((int) Math.random() * node.getScore());

        LearningAgent agent2 = new LearningAgent(gameState, 2);
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

