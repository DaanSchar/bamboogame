package com.maastricht.university.logic.ai.hybrid.environment;

import com.maastricht.university.logic.ai.agent.Agent;
import com.maastricht.university.logic.ai.hybrid.search.ComputeLeafNodes;
import com.maastricht.university.logic.ai.hybrid.search.Search;
import com.maastricht.university.logic.ai.minimax.tree.ITree;
import com.maastricht.university.logic.ai.minimax.tree.ITreeNode;
import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.game.game.Move;
import com.maastricht.university.logic.game.util.interfaces.IGameState;

import java.util.List;

public class LearningAgent extends Agent {

    private List<ITreeNode> leafNodes;
    ITree<GameState> tree;
    private final int depth = 2;

    public LearningAgent(IGameState gameState, int playerNumber) {
        super(gameState, playerNumber);

        initTree();
    }

    public ITreeNode<IGameState> getNextNode() {
        for (int i=0; i < leafNodes.size(); i++)
            if (!leafNodes.get(i).hasScore())
                if ( (i+1) < leafNodes.size() )
                    return leafNodes.get(i + 1);

        return null;
    }

    public ITreeNode<IGameState> getCurrentNode() {
        for(int i=0; i<leafNodes.size(); i++)
            if(!leafNodes.get(i).hasScore())
                return leafNodes.get(i);

        return null;
    }

    public boolean isMovable() {
        return !hasNullNode();
    }

    @Override
    public void move() {
        if (isFirstMove)
            isFirstMove = false;

        Search search = new Search(player);

        if (!hasNullNode()) {
            Move move = search.searchTree(tree.getRoot());
            gameState.move(move.getX(), move.getY(), player);
        }

    }

    public void reset(IGameState gameState) {
        setGameState(gameState);
        initTree();
    }

    public void initTree() {
        ComputeLeafNodes computeLeafNodes = new ComputeLeafNodes(gameState, depth);
        leafNodes = computeLeafNodes.getLeafNodes();
        tree = computeLeafNodes.getTree();
    }

    private boolean hasNullNode() {
        for (int i=0; i<leafNodes.size(); i++)
            if (!leafNodes.get(i).hasScore())
                return true;

        return false;
    }

}
