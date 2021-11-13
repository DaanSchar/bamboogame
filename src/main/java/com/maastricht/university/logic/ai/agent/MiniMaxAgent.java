package com.maastricht.university.logic.ai.agent;

import com.maastricht.university.logic.game.game.Move;
import com.maastricht.university.logic.game.util.interfaces.IGameState;
import java.util.ArrayList;


public class MiniMaxAgent extends Agent{

    public ArrayList<Move> commonLegal;

    public MiniMaxAgent(IGameState gameState, int playerNumber) {
        super(gameState, playerNumber);
    }

    private boolean isMax(int player){
        if(player == super.player){
            return true;
        }else
            return false;
    }
    private void determineMove() {
        ArrayList<Move> maxLegalMoves;
        ArrayList<Move> minLegalMoves;

        if (isMax(player)) {
            maxLegalMoves = gameState.getLegalMoves(player);
            minLegalMoves = gameState.getLegalMoves(1);

            commonLegal = legalMoves(maxLegalMoves, minLegalMoves);
            //choose a legal move that is within the commonLegal list and in the best branch
        } else
            maxLegalMoves = gameState.getLegalMoves(2);
            minLegalMoves = gameState.getLegalMoves(player);

            commonLegal = legalMoves(maxLegalMoves, minLegalMoves);
    }

    private ArrayList<Move> legalMoves(ArrayList<Move> max, ArrayList<Move> min){
        ArrayList<Move> legalMoves = new ArrayList<Move>();
        for (Move moveMin : min) {
            if (max.contains(moveMin)) {
                legalMoves.add(moveMin);
            }
        }

        return legalMoves;
    }
    private Move bestBranch(){
        return null;
    }
}

