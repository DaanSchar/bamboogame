package com.maastricht.university.logic.ai.hybrid;

import com.maastricht.university.logic.ai.agent.Agent;
import com.maastricht.university.logic.ai.agent.Agent2;
import com.maastricht.university.logic.ai.minimax.tree.TreeNode;
import com.maastricht.university.logic.game.game.GameState;


public class searchTest {

    public static void main(String[] args) {
        GameState gameState = new GameState(2, 2);

        Agent2 agent2 = new Agent2(gameState, 2);
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

