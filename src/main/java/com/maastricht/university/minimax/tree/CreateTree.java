package com.maastricht.university.minimax.tree;

import com.maastricht.university.logic.game.game.GameState;

import java.util.List;

/**
 * Creates a full tree base on the ITree interface
 * It takes in a GameState takes that as the basis
 * It can also take in a depth, otherwise unlimited
 */
public class CreateTree {

    ITree<GameState> tree = new Tree<GameState>();

    public CreateTree(GameState state) {

    }

    public CreateTree(GameState state, int depth) {
        for(int i=0; i<depth; i++) {

        }
    }

    private void addChildren(ITreeNode<GameState> parent) {
        GameState parentState = parent.getElement();
        List<Integer[]> moves = parentState.getLegalMoves(parentState.getPlayerTurn());

        for(int i=0; i<moves.size(); i++) {
            GameState childState = parentState.clone();
            childState.move(moves.get(i)[0], moves.get(i)[1], childState.getPlayerTurn());
            parent.addChild(childState);
        }
    }
}
