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

    ITree<GameState> tree = new Tree<GameState>();

    /**
     * Creates a full tree with specified root
     * @param state state of the root
     */
    public CreateTree(GameState state) {
        tree = new Tree<GameState>(state);

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
            frontiers.remove(0);
        }
    }

    /**
     * Creates a full tree with specified root till depth is reached
     * @param state state of the root
     * @param depth depth of the desired tree
     */
    public CreateTree(GameState state, int depth) {
        tree = new Tree<GameState>(state);

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
            frontiers.remove(0);
        }
    }

    public ITree<GameState> getTree() {return tree;}

    private void addChildren(ITreeNode<GameState> parent) {
        GameState parentState = parent.getElement();
        List<Move> moves = parentState.getLegalMoves(parentState.getPlayerTurn());

        for(int i=0; i<moves.size(); i++) {
            GameState childState = parentState.clone();
            childState.move(moves.get(i).getX(), moves.get(i).getY(), childState.getPlayerTurn());
            parent.addChild(childState);
        }
    }






}
