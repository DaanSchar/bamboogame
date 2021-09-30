package com.maastricht.university.frontend;

import com.maastricht.university.logic.game.GameState;
import com.maastricht.university.logic.util.interfaces.IGameState;

public class Factory {

    private static GameState state = new GameState(4,2);

    public static IGameState getGameState() {
        return state;
    }

}
