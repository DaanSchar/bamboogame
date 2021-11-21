package com.maastricht.university.frontend;

import com.maastricht.university.frontend.Factory;
import com.maastricht.university.frontend.WindowUpdater;
import com.maastricht.university.frontend.Main;
import com.maastricht.university.logic.ai.agent.Agent;
import com.maastricht.university.logic.ai.agent.AlphaBetaAgent;
import com.maastricht.university.logic.game.util.interfaces.IGameState;
import javafx.scene.Scene;

public class MiniMax extends Main {

    @Override
    public Scene getScene(){
       createTimerThread();
       return scene;
    }
    public void createTimerThread() {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        gameloop();
                    }
                },
                1000
        );
    }

    private void gameloop() {
        while(Factory.getGameState().winner() == 0) {
            //runDoubleAi();
            runMiniMax();
        }
        System.out.println("winner is " + Factory.getGameState().winner());
    }

    public void runMiniMax(){
        IGameState state =  Factory.getGameState();
        AlphaBetaAgent agent = new AlphaBetaAgent(state, 2);

        agent.move();
        WindowUpdater.update();
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    public void runDoubleAi() {
        IGameState state =  Factory.getGameState();
        Agent agent = new Agent(state, 1);
        Agent agent2 = new Agent(state, 2);

        agent.move();
        WindowUpdater.update();
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
        agent2.move();
        WindowUpdater.update();
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
    }

}

