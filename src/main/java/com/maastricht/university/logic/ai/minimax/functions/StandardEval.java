package com.maastricht.university.logic.ai.minimax.functions;

import com.maastricht.university.logic.game.util.interfaces.IGameState;

public class StandardEval implements IEvaluationFunction {

    public int getPlayerScore(IGameState state, int player) {
        // if the game is over, return either the max or min score based on whether player or opponent won
        if(state.winner() != 0) {
            if(state.winner() == player) {
                //System.out.println("Calculated Score: " + Integer.MAX_VALUE);
                return Integer.MAX_VALUE;
            }
            else{
                //System.out.println("Calculated Score: " + Integer.MIN_VALUE);
                return Integer.MIN_VALUE;
            }
        }

        // + number of groups
        int score = 5*state.getBoard().getGroups(player).size();

        // + number of legal moves player
        score += state.getLegalMoves(player).size();

        // - number of legal moves opponent
        // - 5 * number of groups opponent
        if(player == 1) {
            score -= state.getLegalMoves(2).size();
            score -= 5*state.getBoard().getGroups(2).size();
        }
        else {
            score -= state.getLegalMoves(1).size();
            score -= 5 * state.getBoard().getGroups(1).size();
        }
        //System.out.println("Calculated Score: " +score);
        return score;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
}
