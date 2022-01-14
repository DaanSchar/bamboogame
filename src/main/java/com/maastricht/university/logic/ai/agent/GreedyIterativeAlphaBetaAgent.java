package com.maastricht.university.logic.ai.agent;

import com.maastricht.university.logic.ai.minimax.functions.IEvaluationFunction;
import com.maastricht.university.logic.ai.minimax.tree.ITree;
import com.maastricht.university.logic.ai.minimax.tree.ITreeNode;
import com.maastricht.university.logic.ai.minimax.tree.Tree;
import com.maastricht.university.logic.game.components.TileGroup;
import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.game.game.Move;
import com.maastricht.university.logic.game.util.interfaces.IGameState;

import java.util.List;

public class GreedyIterativeAlphaBetaAgent extends Agent{

    protected final int minPlayer;
    protected long maxTime;
    protected IEvaluationFunction evaluation;

    protected ITree tree;
    protected int maxChildren;

    protected boolean debugDepth = true;
    protected boolean debugFiltering = false;

    /**
     *
     * @param gameState state of the game the agent plays in
     * @param playerNumber number of the agent
     * @param maxTime maximum time allowed to make one move in seconds
     * @param evaluation how the score in leafnodes is calculated
     * @param maxChildren maximum children a node can keep for next iteration
     */
    public GreedyIterativeAlphaBetaAgent(IGameState gameState, int playerNumber, long maxTime, IEvaluationFunction evaluation, int maxChildren) {
        super(gameState, playerNumber);
        if(player == 1) {
            minPlayer = 2;
        }else
            minPlayer = 1;
        this.maxTime = maxTime*1000 -50;
        this.evaluation = evaluation;
        this.maxChildren = maxChildren;
    }

    /**
     *
     * @param maxTime new maximum time allowed to make one move in seconds
     */
    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime*1000 -5;
    }

    /**
     * Make the best move
     */
    @Override
    public void move() {
        //create the tree
        long startTime = System.currentTimeMillis();
        long endTime = startTime + maxTime;

        // if the game isn't over yet and it's this agent's turn
        if (this.gameState.winner() ==0 && this.gameState.getPlayerTurn()==this.player) {
            //create the tree and make global variable
            this.tree = new Tree<GameState>((GameState) gameState, 2);

            //find out how many moves player 1 has done
            int moves1 = 0;
            List<TileGroup> groups1 = this.gameState.getBoard().getGroups(1);
            for(int i=0; i<groups1.size(); i++) {
                moves1 += groups1.get(i).getMembers().size();
            }

            // maxDepth is the number of moves of player 1 and player 2 together
            int amountOfTiles = 72;
            int maxDepth;
            if(this.player==1)
                maxDepth = amountOfTiles - (moves1*2);
            else
                maxDepth = amountOfTiles - (moves1*2) + 1;


            // always take a default bestMove, just a random legal move
            Move bestMove = this.gameState.getLegalMoves(this.player).get(0);

            long timeSearch = 0;
            long startSearch;
            //keep searching with increasing depth as long as the maxTime hasn't passed yet
            //  and depth is smaller as the amount of moves possible
            long currentTime = System.currentTimeMillis();
            for(int depth=1; currentTime<endTime && currentTime+timeSearch<endTime && depth<maxDepth; depth++) {
                startSearch = System.currentTimeMillis();
                Move newMove = search(depth, endTime);
                timeSearch = System.currentTimeMillis() - startSearch;
                currentTime = System.currentTimeMillis();

                //if it finished the search, adjust the bestMove
                if(currentTime<endTime) {
                    bestMove = newMove;
                    if(debugDepth)
                        System.out.println("depth: " + depth + ", time: " + timeSearch);
                }
            }
            //Delete the tree
            this.tree = null;

            //make the move
            this.gameState.move(bestMove.getX(), bestMove.getY(), this.player);
        }
    }

    /**
     * search for the best move
     * @param depth max depth of the tree
     * @param endTime latest time the search can continue till
     * @return the best move
     */
    public Move search(int depth, long endTime) {
        ITreeNode<GameState> root;
        if(this.tree != null)
            root = this.tree.getRoot();
        else {
            ITree<GameState> tree = new Tree<GameState>((GameState) gameState, 2);
            root = tree.getRoot();
        }
        maxValue(root, Integer.MIN_VALUE, Integer.MAX_VALUE, depth, endTime);
        return root.getMaxChild().getLastMove();
    }

    /**
     * computes the maxValue of the branch and adds all children to the tree
     *
     * @param node  root of branch
     * @param alpha biggest value found in branch so far
     * @param beta smallest value found in branch so far
     * @param depth max depth of this branch
     * @param endTime latest time at which the search has to end
     * @return the maxValue of the branch
     */
    private int maxValue(ITreeNode node, int alpha, int beta, int depth, long endTime){
        // Stop the search if over time
        if(System.currentTimeMillis() >= endTime)
            return 0;

        GameState state = (GameState) node.getElement();
        // if a leaf node, return the current score
        if(state.isGameOver() || depth==0) {
            node.setScore(evaluation.getPlayerScore(state, player));
            return node.getScore();
        }
        // if not a leaf node, get maxValue of all children
        int value = Integer.MIN_VALUE;

        //if already children, go through those children and don't create new ones
        if(node.hasChildren()) {
            List children = node.getChildren();
            for (int i = 0; i < children.size(); i++) {
                // keep storing the highest value only
                value = Math.max(value, minValue((ITreeNode) children.get(i), alpha, beta, depth-1, endTime));
            }
            node.setScore(value);

            // Prune
            if(value >= beta)
                return value;
            alpha = Math.max(alpha, value);

            return value;
        }

        //if not already children, create children for all legal moves, but only keep the best ones
        else {
            List<Move> moves = state.getLegalMoves(player);
            for(int i=0; i<moves.size(); i++) {
                // Stop the search if over time
                if(System.currentTimeMillis() >= endTime)
                    return 0;

                Move move = moves.get(i);
                GameState newState = state.clone();
                newState.move(move.getX(), move.getY(), player);
                node.addChild(newState, move);
                ITreeNode<GameState> newNode = (ITreeNode<GameState>) node.getChildren().get(i);
                value = Math.max(value, minValue(newNode, alpha, beta, depth-1, endTime));

                newNode.setScore(value);

                // Prune
                if(value >= beta)
                    return value;
                alpha = Math.max(alpha, value);
            }
            // remove all children that are not in the top
            if(debugFiltering)
                System.out.println("size list before sorting: " + node.getChildren().size());
            node.sortChildren();
            if(debugFiltering)
                System.out.println("size list after sorting: " + node.getChildren().size());
            node.subListChildren(0,this.maxChildren);
            if(debugFiltering)
                System.out.println("size list after filtering: " + node.getChildren().size());



            return value;
        }
    }

    /**
     * computes the minValue of the branch and adds all children to the tree
     * @param node  root of branch
     * @param alpha biggest value found in branch so far
     * @param beta smallest value found in branch so far
     * @param depth max depth of this branch
     * @param endTime latest time at which the search has to end
     * @return the minValue of the branch
     */
    private int minValue(ITreeNode node, int alpha, int beta, int depth, long endTime){
        // Stop the search if over time
        if(System.currentTimeMillis() >= endTime)
            return 0;

        GameState state = (GameState) node.getElement();
        // if a leaf node, return the current score
        if(state.isGameOver() || depth==0) {
            node.setScore(evaluation.getPlayerScore(state, player));
            return node.getScore();
        }
        // if not a leaf node, get minValue of all children
        int value = Integer.MAX_VALUE;

        //if already children, go through those children and don't create new ones
        if(node.hasChildren()) {
            List children = node.getChildren();
            for (int i = 0; i < children.size(); i++) {
                // keep storing the lowest value only
                value = Math.min(value, maxValue((ITreeNode) children.get(i), alpha, beta, depth-1, endTime));
            }
            node.setScore(value);

            // Prune
            if(value <= alpha)
                return value;
            beta = Math.min(beta, value);

            return value;
        }

        //if not already children, create children for all legal moves, but only keep the worst ones
        else {
            List<Move> moves = state.getLegalMoves(minPlayer);
            for(int i=0; i<moves.size(); i++) {
                // Stop the search if over time
                if(System.currentTimeMillis() >= endTime)
                    return 0;

                Move move = moves.get(i);
                GameState newState = state.clone();
                newState.move(move.getX(), move.getY(), minPlayer);
                node.addChild(newState, move);
                ITreeNode<GameState> newNode = (ITreeNode<GameState>) node.getChildren().get(i);
                value = Math.min(value, maxValue(newNode, alpha, beta, depth-1, endTime));

                newNode.setScore(value);

                // Prune
                if(value <= alpha)
                    return value;
                beta = Math.min(beta, value);
            }
            // remove all children that are not in the bottom
            if(debugFiltering)
                System.out.println("size list before sorting: " + node.getChildren().size());
            node.sortChildren();
            if(debugFiltering)
                System.out.println("size list after sorting: " + node.getChildren().size());
            int beginIndex = node.getChildren().size()-this.maxChildren-1;
            node.subListChildren(beginIndex, node.getChildren().size());
            if(debugFiltering)
                System.out.println("size list after filtering: " + node.getChildren().size());

            return value;
        }
    }

    @Override
    public String getName() {
        return "GreedyIterativeAlphaBetaAgent[maxTime=" + maxTime + "][maxChildren=" + maxChildren + "][eval=" + evaluation.getName() + "]";
    }
}

