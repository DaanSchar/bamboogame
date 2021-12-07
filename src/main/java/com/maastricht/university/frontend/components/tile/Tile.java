package com.maastricht.university.frontend.components.tile;

import com.maastricht.university.frontend.Factory;
import com.maastricht.university.frontend.Main;
import com.maastricht.university.frontend.WindowUpdater;
import com.maastricht.university.frontend.components.HoverableButton;
import com.maastricht.university.logic.ai.agent.Agent;
import com.maastricht.university.logic.ai.agent.ReinforcementAgent;
import com.maastricht.university.logic.ai.reinforcement.network.Network;
import com.maastricht.university.logic.game.util.interfaces.IGameState;
import javafx.scene.shape.SVGPath;

public class Tile implements Cloneable {

    private HoverableButton button;

    private int q;
    private int r;

    public Tile(int q, int r, double x, double y, double size)  {
        this.q = q;
        this.r = r;

        initButton(x, y, size);
    }

    public HoverableButton getButton() {
        return button;
    }

    private void initButton(double x, double y, double size) {
        button = new HoverableButton(x, y, size, size);
        button.setColor(TileColor.NONE.get());
        SVGPath circle = new SVGPath();
        circle.setContent("M25 20" +
                "a5 5 0 1 1-10 0 " +
                "5 5 0 1 1 10 0z"
        );
        button.setShape(circle);
        setClickEvent();
    }

    private void setClickEvent() {
        button.setOnMouseClicked(e -> {
            IGameState game = Factory.getGameState();

            if (Factory.getGameMode() == 0) {
                //System.out.println("bruh");
                game.move(q, r, game.getPlayerTurn());
            }

            if (Factory.getGameMode() == 1) {
                game.move(q, r, 1);
                Agent agent = Factory.getAgent(Factory.getAgentType());
                agent.move();
            }

            WindowUpdater.update(Factory.getGameState());

            winner(game);
        });
    }

    private void winner(IGameState game) {
        if(game.winner() != 0){
            Main.isWinner();
        }
    }


}
