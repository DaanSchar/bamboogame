package com.maastricht.university.frontend;

import com.maastricht.university.frontend.components.tile.TileMap;
import com.maastricht.university.frontend.scenes.GameScreen;
import com.maastricht.university.frontend.scenes.Menu;
import com.maastricht.university.logic.ai.agent.Agent;
import com.maastricht.university.logic.ai.agent.AlphaBetaAgent;
import com.maastricht.university.logic.ai.agent.ReinforcementAgent;
import com.maastricht.university.logic.ai.minimax.functions.StandardEval;
import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.ai.minimax.functions.ReinforceEval;
import com.maastricht.university.logic.game.game.GameStateFactory;
import com.maastricht.university.logic.game.util.interfaces.IGameState;
import javafx.scene.Scene;

import java.util.Random;

public class Factory {

    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 500;

    // GameMode 0 = Human vs Human
    // GameMode 1 = Human vs AI
    private static int GameMode = 0;

    // agent 0 = Reinforcement
    // agent 1 = AlphaBeta
    // agent 2 = Hybrid
    private static int agentType = 0;

    private static final TileMap tilemap = new TileMap(9, SCREEN_WIDTH /4,SCREEN_HEIGHT/6,30);

    private static final Agent ReinforcementAgent = new ReinforcementAgent(GameStateFactory.getGameState(), 2, "src/main/resources/networks/phase 2/network-81-1-1-1E.zip");
    private static final Agent abAgent = new AlphaBetaAgent(GameStateFactory.getGameState(), 2, 4,  new StandardEval());
    private static final Agent abAgent2 = new AlphaBetaAgent(GameStateFactory.getGameState(),2,4, new ReinforceEval("src/main/resources/networks/hybrid/newNetwork/Hybrid-wR100-wR40-lR0.01-mE1.zip"));

    private static final Menu menu = new Menu();
    private static final GameScreen gameScreen = new GameScreen();

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

    public static Scene getMenuScene() {
        return menu.getScene();
    }

    public static Scene getGameScene() {
        return gameScreen.getScene();
    }

    public static Agent getAgent(int agentType) {
        if (agentType == 0)
            return ReinforcementAgent;
        else if (agentType == 1)
            return abAgent;
        else if(agentType == 2)
            return abAgent2;

        return null;
    }
}
