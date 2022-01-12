package com.maastricht.university.logic.game.game;

import com.maastricht.university.logic.game.util.interfaces.IGameState;

public class GameStateFactory {

    private static final GameState state = new GameState(4,2);

    public static IGameState getGameState() {
        return state;
    }

    public static void resetGameState() {
        state.init(4, 2);
    }

}
