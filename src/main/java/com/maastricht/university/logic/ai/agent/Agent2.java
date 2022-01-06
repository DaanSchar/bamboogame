package com.maastricht.university.logic.ai.agent;

import com.maastricht.university.logic.ai.hybrid.ComputeLeafNodes;
import com.maastricht.university.logic.ai.hybrid.Search;
import com.maastricht.university.logic.ai.minimax.tree.ITree;
import com.maastricht.university.logic.ai.minimax.tree.ITreeNode;
import com.maastricht.university.logic.game.components.Board;
import com.maastricht.university.logic.game.game.Move;
import com.maastricht.university.logic.game.util.interfaces.IGameState;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
     * Make a random legal move
     */
    private void determineRandomMove() {
        Random rand = new Random();
        ArrayList<Move> moveList = gameState.getLegalMoves(player);
        int index = rand.nextInt(moveList.size());
        Move move = moveList.get(index);

        System.out.println("Move: (" +move.getX()+ ", " +move.getY()+ ", " +player+ ")");
        gameState.move(move.getX(), move.getY(), player);
    }

    /**
     * Make a move that doesn't join groups together (if possible), otherwise random
     */
    private void determineMove() {
        Board board = gameState.getBoard();

        Random rand = new Random();
        ArrayList<Move> moveList = gameState.getLegalMoves(player);
        ArrayList<Move> betterMoves = new ArrayList<>();

        //If none of the moves has neighbouring groups of equal color its a non-connecting move
        // hopefully better
        for(Move m : moveList) {
            if(board.getNeighboringGroups(m.getX(), m.getY(), player).size() == 0)
                betterMoves.add(m);
        }

        if(betterMoves.size() != 0) {
            int index = rand.nextInt(betterMoves.size());
            Move move = betterMoves.get(index);
            gameState.move(move.getX(), move.getY(), player);
            System.out.println("Move: (" + move.getX() + ", " + move.getY() + ", " + player + ")");
        }
        else {
            int index = rand.nextInt(moveList.size());
            Move move = moveList.get(index);
            gameState.move(move.getX(), move.getY(), player);
            System.out.println("Move: (" + move.getX() + ", " + move.getY() + ", " + player + ")");
        }
    }

    /**
     *
     * @return current state of game
     */
    public IGameState getGameState() {
        return gameState;
    }
}

