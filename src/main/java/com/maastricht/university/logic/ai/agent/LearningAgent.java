package com.maastricht.university.logic.ai.agent;

import com.maastricht.university.logic.game.util.interfaces.IGameState;

public class LearningAgent extends Agent {

    public LearningAgent(IGameState gameState, int playerNumber) {
        super(gameState, playerNumber);
    }

    public void setNextLeaf(int score) {
        // set the score of the next leaf
    }

    public void move() {
        // if the whole tree has been computed, explore the tree and chose the most optimal move
    }

}
