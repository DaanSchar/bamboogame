package com.maastricht.university.logic.ai.agent;

import com.maastricht.university.logic.ai.agent.Agent;
import com.maastricht.university.logic.ai.agent.IAgent;
import com.maastricht.university.logic.game.util.interfaces.IGameState;

public class ReinforcementAgent extends Agent {

    public ReinforcementAgent(IGameState gameState, int playerNumber) {
        super(gameState, playerNumber);
    }
}
