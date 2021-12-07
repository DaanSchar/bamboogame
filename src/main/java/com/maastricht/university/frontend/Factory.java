package com.maastricht.university.frontend;

import com.maastricht.university.frontend.components.tile.TileMap;
import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.game.util.interfaces.IGameState;

public class Factory {

    private static int SCREEN_WIDTH = 800;
    private static int SCREEN_HEIGHT = 500;

    // GameMode 0 = Human vs Human
    // GameMode 1 = Human vs AI
    private static int GameMode = 0;

    private static GameState state = new GameState(4,2);
    private static TileMap tilemap = new TileMap(9, SCREEN_WIDTH /4,SCREEN_HEIGHT/6,30);

    public static IGameState getGameState() {
        return state;
    }

    public static void resetGameState() {
        state = new GameState(4, 2);
    }

    public static TileMap getTileMap() {
        return tilemap;
    }

    public static int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public static int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    public static int getGameMode() {
        return GameMode;
    }

    public static void setGameMode(int gameMode) {
        GameMode = gameMode;
    }
}
