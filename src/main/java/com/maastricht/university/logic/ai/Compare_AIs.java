package com.maastricht.university.logic.ai;

import com.maastricht.university.frontend.Factory;
import com.maastricht.university.frontend.WindowUpdater;
import com.maastricht.university.logic.ai.agent.*;
import com.maastricht.university.logic.ai.minimax.tree.AlphaBetaSearchTree;
import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.game.game.Move;
import com.maastricht.university.logic.game.util.interfaces.IGameState;

import java.util.Date;

public class Compare_AIs {

    private static boolean DEBUG = false;

    public static void main(String[] args) {
        //runAlphaBeta_vs_RL(100000);
        double[][] moveTime = compare_AB_Minimax(100, 1, 3);
        System.out.println(moveTime);
    }

    public static double[][] compare_AB_Minimax(int minMoves, int minDepth, int maxDepth) {
        String randomNetworkName = "src/main/resources/networks/network-81-1-1-1E.zip"; // + System.currentTimeMillis() + ".zip";
        Date date = new Date();
        double[][] moveTime = new double[2][maxDepth-minDepth+1];

        boolean gameOver = false;
        // for every depth
        for (int depth = minDepth; depth <= maxDepth; depth++) {
            float timeAB = 0;
            float timeMM = 0;
            int moves = 0;
            // run games while not enough data yet
            while (moves < minMoves) {
                gameOver = false;
                //play a game
                IGameState state = new GameState(4, 2);
                MiniMaxAgent agent1 = new MiniMaxAgent(state, 1, depth);
                AlphaBetaAgent agent2a = new AlphaBetaAgent(state, 2, depth);
                AlphaBetaSearchTree agent2b = new AlphaBetaSearchTree();
                // keep going until the game is finished
                while (state.winner() == 0) {
                    moves++;
                    agent1.move();
                    long milisec = System.currentTimeMillis();
                    Move move2a = agent2a.search(4);
                    float timeTaken = System.currentTimeMillis() - milisec;
                    timeAB += timeTaken;
                    if (DEBUG) {
                        System.out.println("Move 2a: (" + move2a.getX() + ", " + move2a.getY() + ")");
                        System.out.println("time taken a : " + timeTaken + " milisec");
                    }
                    milisec = System.currentTimeMillis();
                    Move move2b = agent2b.search((GameState) state, 2, 4);
                    timeTaken = System.currentTimeMillis() - milisec;
                    timeMM += timeTaken;
                    if (DEBUG) {
                        System.out.println("Move 2b: (" + move2b.getX() + ", " + move2b.getY() + ")");
                        System.out.println("time taken b : " + timeTaken + " milisec");
                        System.out.println();
                    }

                    state.move(move2a.getX(), move2b.getY(), 2);
                    if (move2a.getX() != move2b.getX() || move2a.getY() != move2b.getY()) {
                        System.out.println();
                        System.out.println("--------------------NOT THE SAME MOVE----------------------------");
                        System.out.println("Move 2a: (" + move2a.getX() + ", " + move2a.getY() + ")");
                        System.out.println("Move 2b: (" + move2b.getX() + ", " + move2b.getY() + ")");
                        System.out.println("--------------------NOT THE SAME MOVE----------------------------");
                        System.out.println();
                    }
                }
                if (state.winner() != 0) {
                    gameOver = true;
                    moveTime[0][depth-minDepth] = (timeAB / 1000) / moves;
                    moveTime[1][depth-minDepth] = (timeMM / 1000) / moves;
                }

                System.out.println();
                //System.out.println("games played: " + (i+1));
                System.out.println("depth: " + depth);
                System.out.println("time AB sec/move:          " + ((timeAB / 1000) / moves));
                System.out.println("time minimax sec/move:     " + ((timeMM / 1000) / moves));
                System.out.println("moves played: " + moves);
                System.out.println();
            }
        }
        return moveTime;
    }


        /*for (int i = 0; i < times; i++) {
            IGameState state = new GameState(4, 2);
            MiniMaxAgent agent1 = new MiniMaxAgent(state, 1, 4);
            AlphaBetaAgent agent2a = new AlphaBetaAgent(state, 2, 4);
            AlphaBetaSearchTree agent2b = new AlphaBetaSearchTree();
            while (state.winner() == 0) {
                moves++;
                agent1.move();
                long milisec = System.currentTimeMillis();
                Move move2a = agent2a.search(4);
                float timeTaken = System.currentTimeMillis() - milisec;
                timeABa += timeTaken;
                if(DEBUG) {
                    System.out.println("Move 2a: (" + move2a.getX() + ", " + move2a.getY() + ")");
                    System.out.println("time taken a : " + timeTaken +" milisec");
                }
                milisec = System.currentTimeMillis();
                Move move2b = agent2b.search((GameState) state, 2, 4);
                timeTaken = System.currentTimeMillis() - milisec;
                timeABb += timeTaken;
                if(DEBUG) {
                    System.out.println("Move 2b: (" + move2b.getX() + ", " + move2b.getY() + ")");
                    System.out.println("time taken b : " + timeTaken +" milisec");
                    System.out.println();
                }

                state.move(move2a.getX(), move2b.getY(), 2);
                if (move2a.getX() != move2b.getX() || move2a.getY() != move2b.getY()) {
                    System.out.println();
                    System.out.println("--------------------NOT THE SAME MOVE----------------------------");
                    System.out.println("Move 2a: (" + move2a.getX() + ", " + move2a.getY() + ")");
                    System.out.println("Move 2b: (" + move2b.getX() + ", " + move2b.getY() + ")");
                    System.out.println("--------------------NOT THE SAME MOVE----------------------------");
                    System.out.println();
                }
            }
            String winner;
            if (state.winner() == 1) {
                winner = "Random agent";
                winRan += 1;
            } else {
                winner = "alpha-beta agent";
                winAB += 1;
            }
            System.out.println();
            System.out.println("winner = " +winner);
            System.out.println("winrate Random:     " + (winRan/(winAB+winRan)));
            System.out.println("winrate alpha-beta: " + (winAB/(winAB+winRan)));
            System.out.println("games played: " + (i+1));
            System.out.println("time agent sec/move:    " + ((timeABa/1000)/moves));
            System.out.println("time tree sec/move:     " + ((timeABb/1000)/moves));
            System.out.println("moves played: " + moves);
            System.out.println();
        }
        */



    public static void runAlphaBeta_vs_RL(int times) {

        double winRL = 0;
        double winAB = 0;
        for(int i=0; i<times; i++) {
            IGameState state =  new GameState(4, 2);
            String randomNetworkName = "src/main/resources/networks/network-81-1-1-1E.zip"; // + System.currentTimeMillis() + ".zip";
            ReinforcementAgent agent1 = new ReinforcementAgent(state, 1, randomNetworkName);
            SemiRandomABAgent agent2 = new SemiRandomABAgent(state, 2, 4);
            while(state.winner()==0) {
                agent1.move();
                agent2.move();
            }
            String winner;
            if(state.winner()==1) {
                winner = "Reinforcement agent";
                winRL += 1;
            }
            else {
                winner = "alpha-beta agent";
                winAB += 1;
            }
            System.out.println();
            System.out.println("winner = " +winner);
            System.out.println("winrate Reinforcement: " + (winRL/(winAB+winRL)));
            System.out.println("winrate alpha-beta:    " + (winAB/(winAB+winRL)));
            System.out.println("games played: " + (i+1));
            System.out.println();
        }


    }
}
