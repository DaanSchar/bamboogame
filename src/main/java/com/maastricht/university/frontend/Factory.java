package com.maastricht.university.frontend;

import com.maastricht.university.frontend.components.tile.TileMap;
import com.maastricht.university.logic.ai.agent.Agent;
import com.maastricht.university.logic.ai.agent.AlphaBetaAgent;
import com.maastricht.university.logic.ai.agent.ReinforcementAgent;
import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.game.util.interfaces.IGameState;

public class Factory {

    private static int SCREEN_WIDTH = 800;
    private static int SCREEN_HEIGHT = 500;

    // GameMode 0 = Human vs Human
    // GameMode 1 = Human vs AI
    private static int GameMode = 0;

    // agent 0 = Reinforcement
    // agent 1 = AlphaBetaPruning
    private static int agentType = 0;

    private static GameState state = new GameState(4,2);
    private static TileMap tilemap = new TileMap(9, SCREEN_WIDTH /4,SCREEN_HEIGHT/6,30);

    private static Agent ReinforcementAgent = new ReinforcementAgent(state, 2, "src/main/resources/networks/network-74-12L.zip");
    private static Agent abAgent = new AlphaBetaAgent(state, 2, 4);

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

    public static int getAgentType() {
        return agentType;
    }

    public static void setAgentType(int agentType) {
        Factory.agentType = agentType;
    }

    public static Agent getAgent(int agentType) {
        if (agentType == 0)
            return ReinforcementAgent;
        else if (agentType == 1)
            return abAgent;

        return null;
    }
}
