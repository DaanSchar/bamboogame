package com.maastricht.university.logic.ai;

import com.maastricht.university.logic.ai.agent.*;
import com.maastricht.university.logic.ai.minimax.functions.StandardEval;
import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.game.game.Move;
import com.maastricht.university.logic.ai.minimax.functions.IEvaluationFunction;
import com.maastricht.university.logic.game.util.interfaces.IGameState;

import java.util.Date;

public class Compare_AIs {

    private static boolean DEBUG = false;

    public static void main(String[] args) {
        //runAlphaBetaVsRL(100000);
        //double[][] moveTime = compareABMinimax(100, 1, 3);
        //double[] moveTime = getTimeDepthAB(100, 4, 6);
//        double[][] moveTime = compareABVsAB2(100, 1, 3);
//        System.out.println(moveTime);
//        testIterativeAlphaBeta();
        testGreedyIterativeAlphaBeta_vs_notGreedy();
    }

    public static void testGreedyIterativeAlphaBeta_vs_notGreedy() {
        GameState state = new GameState(4,2);
        IterativeAlphaBetaAgent agent1 = new IterativeAlphaBetaAgent(state, 1, 10, new StandardEval());
        GreedyIterativeAlphaBetaAgent agent2 = new GreedyIterativeAlphaBetaAgent(state, 2, 10, new StandardEval(), 3);
        while(!state.isGameOver()) {
            long startSearch = System.currentTimeMillis();
            agent1.move();
            long searchTime = System.currentTimeMillis() - startSearch;
            System.out.println("agent1 time: " + searchTime);
            System.out.println();

            startSearch = System.currentTimeMillis();
            agent2.move();
            searchTime = System.currentTimeMillis() - startSearch;
            System.out.println("agent2 time: " + searchTime);
            System.out.println();
        }
        System.out.println("winner: " + state.winner());
    }

    public static void testIterativeAlphaBeta() {
        GameState state = new GameState(4,2);
        IterativeAlphaBetaAgent agent1 = new IterativeAlphaBetaAgent(state, 1, 1, new StandardEval());
        IterativeAlphaBetaAgent agent2 = new IterativeAlphaBetaAgent(state, 2, 10, new StandardEval());
        while(!state.isGameOver()) {
            long startSearch = System.currentTimeMillis();
            agent1.move();
            long searchTime = System.currentTimeMillis() - startSearch;
            System.out.println("agent1 time: " + searchTime);
            System.out.println();

            startSearch = System.currentTimeMillis();
            agent2.move();
            searchTime = System.currentTimeMillis() - startSearch;
            System.out.println("agent2 time: " + searchTime);
            System.out.println();
        }
        System.out.println("winner: " + state.winner());
    }

    public static double[] getTimeDepthAB(int minMoves, int minDepth, int maxDepth) {
        String randomNetworkName = "src/main/resources/networks/network-81-1-1-1E.zip";
        Date date = new Date();
        double[] moveTime = new double[maxDepth-minDepth+1];

        // for every depth
        for (int depth = minDepth; depth <= maxDepth; depth++) {
            double timeAB = 0;
            int moves = 0;
            // run games while not enough data yet
            while (moves < minMoves) {
                //play a game
                IGameState state = new GameState(4, 2);
                RandomAgent agent1 = new RandomAgent(state, 1);
                AlphaBetaAgent agent2 = new AlphaBetaAgent(state, 2, depth, (IEvaluationFunction) new StandardEval());
                // keep going until the game is finished
                while (state.winner() == 0) {
                    moves++;
                    agent1.move();
                    double milisec = System.currentTimeMillis();
                    agent2.move();
                    double timeTaken = System.currentTimeMillis() - milisec;
                    timeAB += timeTaken;
                }
                if (state.winner() != 0) {
                    moveTime[depth-minDepth] = (timeAB / 1000) / moves;
                }

                System.out.println();
                //System.out.println("games played: " + (i+1));
                System.out.println("depth: " + depth);
                System.out.println("time AB sec/move:          " + ((timeAB / 1000) / moves));
                System.out.println("moves played: " + moves);
                System.out.println();
            }
        }
        return moveTime;
    }

    public static double[][] compareABVsAB2(int minMoves, int minDepth, int maxDepth) {
        String randomNetworkName = "src/main/resources/networks/network-81-1-1-1E.zip"; // + System.currentTimeMillis() + ".zip";
        Date date = new Date();
        double[][] moveTime = new double[2][maxDepth-minDepth+1];

        // for every depth
        for (int depth = minDepth; depth <= maxDepth; depth++) {
            double timeAB = 0;
            double timeMM = 0;
            int moves = 0;
            // run games while not enough data yet
            while (moves < minMoves) {
                //play a game
                IGameState state = new GameState(4, 2);
                RandomAgent agent1 = new RandomAgent(state, 1);
                AlphaBetaAgent agent2a = new AlphaBetaAgent(state, 2, depth, (IEvaluationFunction) new StandardEval());
                //TODO: put the semi-random evaluation function in agent2b
                AlphaBetaAgent agent2b = new AlphaBetaAgent(state, 2, depth, (IEvaluationFunction) new StandardEval());
                // keep going until the game is finished
                while (state.winner() == 0) {
                    moves++;
                    agent1.move();
                    double milisec = System.currentTimeMillis();
                    Move move2a = agent2a.search(depth);
                    double timeTaken = System.currentTimeMillis() - milisec;
                    timeAB += timeTaken;
                    if (DEBUG) {
                        System.out.println("Move 2a: (" + move2a.getX() + ", " + move2a.getY() + ")");
                        System.out.println("time taken a : " + timeTaken + " milisec");
                    }
                    milisec = System.currentTimeMillis();
                    Move move2b = agent2b.search(depth);
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

    public static double[][] compareABMinimax(int minMoves, int minDepth, int maxDepth) {
        String randomNetworkName = "src/main/resources/networks/network-81-1-1-1E.zip"; // + System.currentTimeMillis() + ".zip";
        Date date = new Date();
        double[][] moveTime = new double[2][maxDepth-minDepth+1];

        // for every depth
        for (int depth = minDepth; depth <= maxDepth; depth++) {
            double timeAB = 0;
            double timeMM = 0;
            int moves = 0;
            // run games while not enough data yet
            while (moves < minMoves) {
                //play a game
                IGameState state = new GameState(4, 2);
                RandomAgent agent1 = new RandomAgent(state, 1);
                AlphaBetaAgent agent2a = new AlphaBetaAgent(state, 2, depth, (IEvaluationFunction) new StandardEval());
                MiniMaxAgent agent2b = new MiniMaxAgent(state, 2, depth);
                // keep going until the game is finished
                while (state.winner() == 0) {
                    moves++;
                    agent1.move();
                    double milisec = System.currentTimeMillis();
                    Move move2a = agent2a.search(depth);
                    double timeTaken = System.currentTimeMillis() - milisec;
                    timeAB += timeTaken;
                    if (DEBUG) {
                        System.out.println("Move 2a: (" + move2a.getX() + ", " + move2a.getY() + ")");
                        System.out.println("time taken a : " + timeTaken + " milisec");
                    }
                    milisec = System.currentTimeMillis();
                    Move move2b = agent2b.search(depth);
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

    public static void runAlphaBetaVsRL(int times) {

        double winRL = 0;
        double winAB = 0;
        for(int i=0; i<times; i++) {
            IGameState state =  new GameState(4, 2);
            String randomNetworkName = "src/main/resources/networks/network-81-1-1-1E.zip"; // + System.currentTimeMillis() + ".zip";
            ReinforcementAgent agent1 = new ReinforcementAgent(state, 1, randomNetworkName);
            SemiRandomABAgent agent2 = new SemiRandomABAgent(state, 2, 4, (IEvaluationFunction) new StandardEval());
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
