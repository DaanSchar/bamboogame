package com.maastricht.university.logic.ai;

import com.maastricht.university.frontend.Factory;
import com.maastricht.university.frontend.WindowUpdater;
import com.maastricht.university.logic.ai.agent.Agent;
import com.maastricht.university.logic.ai.agent.AlphaBetaAgent;
import com.maastricht.university.logic.ai.agent.ReinforcementAgent;
import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.game.util.interfaces.IGameState;

public class Compare_AIs {

    public static void main(String[] args) {
        runAlphaBeta_vs_RL(100);
    }

    public static void runAlphaBeta_vs_RL(int times) {

        int winRL = 0;
        int winAB = 0;
        for(int i=0; i<times; i++) {
            IGameState state =  new GameState(4, 2);
            String randomNetworkName = "src/main/resources/networks/network-81-1-1-1E.zip"; // + System.currentTimeMillis() + ".zip";
            ReinforcementAgent agent1 = new ReinforcementAgent(state, 1, randomNetworkName);
            AlphaBetaAgent agent2 = new AlphaBetaAgent(state, 2, 4);
            while(state.winner()==0) {
                agent1.move();
                agent2.move();
            }
            String winner;
            if(state.winner()==1) {
                winner = "Reinforcement agent";
                winRL++;
            }
            else {
                winner = "alpha-beta agent";
                winAB++;
            }
            System.out.println("winner = " +winner);
            System.out.println("winrate Reinforcement: " + (winRL/(winAB+winRL)));
            System.out.println("winrate alpha-beta:    " + (winAB/(winAB+winRL)));
            System.out.println("games played: " + i+1);
            System.out.println();
        }


    }
}
