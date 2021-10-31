package com.maastricht.university.minimax.tree;

import com.maastricht.university.logic.game.game.GameState;

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

    }
}
