package com.maastricht.university.logic.ai.minimax.tree;
import com.maastricht.university.logic.game.game.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates a full tree base on the ITree interface
 * It takes in a GameState takes that as the basis
 * It can also take in a depth, otherwise unlimited
 */
public class CreateTree {

    ITree<GameState> tree;
    public boolean DEBUG = false;

    /**
     * Creates a full tree with specified root
     * adds score in all leafNodes
     * @param state state of the root
     */
    public CreateTree(GameState state) {
        tree = new Tree<GameState>(state, state.getNumberOfPlayers());

        ArrayList<ITreeNode<GameState>> frontiers = new ArrayList<ITreeNode<GameState>>();
        frontiers.add(tree.getRoot());

        while(frontiers.size() != 0) {
            ITreeNode<GameState> parent = frontiers.get(0);

            if(!parent.getElement().isGameOver()) {
                addChildren(parent);
                List<ITreeNode<GameState>> children = parent.getChildren();
                for (int j = 0; j < children.size(); j++)
                    frontiers.add(children.get(j));
            }
            //set the score of the leaf TreeNode
            else {
                parent.setScore(parent.getScore());
            }
            frontiers.remove(0);
        }
    }

    /**
     * Creates a full tree with specified root till depth is reached
     * @param state state of the root
     * @param depth depth of the desired tree
     */
    public CreateTree(GameState state, int depth) {
        tree = new Tree<GameState>(state, state.getNumberOfPlayers());

        ArrayList<ITreeNode<GameState>> frontiers = new ArrayList<ITreeNode<GameState>>();
        frontiers.add(tree.getRoot());

        while(frontiers.size() != 0) {
            ITreeNode<GameState> parent = frontiers.get(0);

            if(parent.getDepth() < depth && !parent.getElement().isGameOver()) {
                addChildren(parent);
                List<ITreeNode<GameState>> children = parent.getChildren();
                for (int j = 0; j < children.size(); j++)
                    frontiers.add(children.get(j));
            }
            //set the score of the leaf TreeNode
            else {
                parent.setScore(parent.getScore());
            }
            frontiers.remove(0);
        }
    }

    public void depthSearch() {

    }

    public ITree<GameState> getTree() {return tree;}

    private void addChildren(ITreeNode<GameState> parent) {
        if(DEBUG)
            System.out.println("A new call of 'addChildren'");
        GameState parentState = parent.getElement();

        if(parent.getDepth() > 1) {
            if(DEBUG) {
                System.out.println("Last move parent:");
                System.out.println("Move: (x=" + parent.getLastMove().getX() + ", y=" + parent.getLastMove().getY() + ")");
            }
        }
        List<Move> moves = parentState.getLegalMoves(parentState.getPlayerTurn());
        if(DEBUG)
            System.out.println("amount of moves to try:" + moves.size());

        for(Move move: moves) {
            GameState childState = parentState.clone();
            if(DEBUG)
                System.out.println("new Move: (x=" +move.getX()+ ", y=" +move.getY()+ ", c=" + childState.getPlayerTurn() + ")");
            childState.move(move.getX(), move.getY(), childState.getPlayerTurn());
            parent.addChild(childState, move);
        }
    }
}