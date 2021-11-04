package com.maastricht.university.frontend;

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
            game.move(q,r,Factory.getGameState().getPlayerTurn());

            Factory.getTileMap().showLegalMoves();

            updateTotalGroupsText(game);
            updateCurrentPlayerText(game);
            winner(game);
        });
    }

    private void updateCurrentPlayerText(IGameState game){
        Main.currentPlayer.setText(Main.currentPlayer());
    }

    private void updateTotalGroupsText(IGameState game){
        Main.p1Text.setText(Integer.toString(game.getTotalGroups(1)));
        Main.p2Text.setText(Integer.toString(game.getTotalGroups(2)));
    }

    private void winner(IGameState game){
        if(game.winner() != 0){
            Main.isWinner();
        }
    }


}
