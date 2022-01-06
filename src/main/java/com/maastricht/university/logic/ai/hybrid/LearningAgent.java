package com.maastricht.university.logic.ai.hybrid;

import com.maastricht.university.logic.ai.agent.Agent;
import com.maastricht.university.logic.ai.minimax.tree.ITree;
import com.maastricht.university.logic.ai.minimax.tree.ITreeNode;
import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.game.game.Move;
import com.maastricht.university.logic.game.util.interfaces.IGameState;

import java.util.List;

public class LearningAgent extends Agent {

    private List<ITreeNode> leafNodes;
    ITree<GameState> tree;
    private boolean isFirstMove;
    private final int depth = 2;

    public LearningAgent(IGameState gameState, int playerNumber) {
        super(gameState, playerNumber);
        this.isFirstMove = true;

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
        return !hasNullNode() || isFirstMove;
    }

    @Override
    public void move() {
        if (isFirstMove)
            isFirstMove = false;

        Search search = new Search(player);

        if (!hasNullNode()) {
            Move move = search.searchTree(tree.getRoot());
            System.out.println("Move for Learning: (" + move.getX() + ", " + move.getY() + ", " + player + ")");
            gameState.move(move.getX(), move.getY(), player);
        }

    }

    public void initTree() {
        ComputeLeafNodes computeLeafNodes = new ComputeLeafNodes(gameState, depth);
        leafNodes = computeLeafNodes.getLeafNodes();
        tree = computeLeafNodes.getTree();
    }

    public void setGameState(IGameState gameState) {
        super.setGameState(gameState);
        initTree();
    }

    private boolean hasNullNode() {
        for (int i=0; i<leafNodes.size(); i++)
            if (!leafNodes.get(i).hasScore())
                return true;

        return false;
    }

}
