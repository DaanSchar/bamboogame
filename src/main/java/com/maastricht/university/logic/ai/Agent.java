package com.maastricht.university.logic.ai;

import com.maastricht.university.logic.game.util.interfaces.IGameState;

import java.util.Random;

public class Agent {

    private IGameState gameState;
    private int player;
    private Random rand;

    public Agent(IGameState gameState, int playerNumber) {
        this.gameState = gameState;
        this.player = playerNumber;
        rand = new Random();
    }

    public void move() {
        while (gameState.getPlayerTurn() == 2 && gameState.winner() == 0) {
            int q = rand.nextInt(9);
            int r = rand.nextInt(9);

            gameState.move(q, r, player);
        }
    }
}
