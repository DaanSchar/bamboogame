package com.maastricht.university.logic.ai.agent;

import com.maastricht.university.logic.ai.hybrid.search.ComputeLeafNodes;
import com.maastricht.university.logic.ai.hybrid.search.Search;
import com.maastricht.university.logic.ai.minimax.tree.ITreeNode;
import com.maastricht.university.logic.game.game.Move;
import com.maastricht.university.logic.game.util.interfaces.IGameState;
import java.util.List;


public class Agent2 implements IAgent{

    protected IGameState gameState;
    protected int player;

    public Agent2(IGameState gameState, final int playerNumber) {
        this.gameState = gameState;
        this.player = playerNumber;
    }

    /**
     * Make a move
     */
    public void move() {
        // Set scores to all the leafNodes
        ComputeLeafNodes computeLeafNodes = new ComputeLeafNodes(gameState, 2);
        List<ITreeNode> leafNodes = computeLeafNodes.getLeafNodes();
        for(int i=0; i<leafNodes.size(); i++) {
            int randomNum = -100 + (int)(Math.random()*(100 + 100 +1));
            leafNodes.get(i).setScore(randomNum);
        }

        //Search tree for best move
        Search search = new Search(player);
        Move move;
        if(gameState.getPlayerTurn() == player && gameState.winner() == 0) {
            move = search.searchTree(computeLeafNodes.getTree().getRoot());

            // Execute move
            gameState.move(move.getX(), move.getY(), player);
            System.out.println("Move for Search: (" + move.getX() + ", " + move.getY() + ", " + player + ")");
        }
    }

    /**
     *
     * @return the player the agent is
     */
    public int getPlayer() {
        return player;
    }

    /**
     * Set the gameState the agent operates in
     * @param gameState the new gameState
     */
    public void setGameState(IGameState gameState) {
        this.gameState = gameState;
    }

    /**
     *
     * @return current state of game
     */
    public IGameState getGameState() {
        return gameState;
    }

    public String getName() {
        return "Agent2";
    }
}

