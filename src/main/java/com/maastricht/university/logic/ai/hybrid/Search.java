package com.maastricht.university.logic.ai.hybrid;

import com.maastricht.university.logic.ai.minimax.tree.ITree;
import com.maastricht.university.logic.game.util.interfaces.IGameState;

public class Search {
    public ITree tree;

    public Search(IGameState gameState){
        ComputeLeafNodes computeLeafNodes = new ComputeLeafNodes(gameState, 4);
        tree = computeLeafNodes.getTree();
    }
    public void miniMax(){

    }
}
