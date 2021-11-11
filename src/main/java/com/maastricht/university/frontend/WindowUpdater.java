package com.maastricht.university.frontend;

import com.maastricht.university.frontend.components.HoverableButton;
import com.maastricht.university.frontend.components.tile.Tile;
import com.maastricht.university.frontend.components.tile.TileColor;
import com.maastricht.university.frontend.components.tile.TileMap;
import com.maastricht.university.logic.game.components.Hexagon;
import com.maastricht.university.logic.game.util.interfaces.IGameState;
import javafx.application.Platform;

public class WindowUpdater {

    public static void update(IGameState state) {
        updateTileColors(state);
        updateCurrentPlayerLabel(state);
        updateTotalGroupsLabel(state);
    }

    private static void updateCurrentPlayerLabel(IGameState state) {
        Platform.runLater(() -> {
            Main.currentPlayer.setText(Integer.toString(state.getPlayerTurn()));
        });
    }

    private static void updateTotalGroupsLabel(IGameState state) {
        Platform.runLater(() -> {
            IGameState game = Factory.getGameState();
            Main.p1Text.setText(Integer.toString(game.getTotalGroups(1)));
            Main.p2Text.setText(Integer.toString(game.getTotalGroups(2)));
        });
    }

    public static void updateTileColors(IGameState state) {
        TileMap tileMap = Factory.getTileMap();

        for (int i = 0; i < tileMap.getDimension(); i++)
            for (int j = 0; j < tileMap.getDimension(); j++)
                if (tileMap.getHexagon().get(i,j) != null)
                    determineTileColor(i, j, tileMap.getHexagon(), state);
    }

    private static void determineTileColor(int q, int r, Hexagon<Tile> hexagon, IGameState state) {
//        IGameState state = Factory.getGameState();
        int playerColor = state.getPlayerColorOfTile(q,r);
        HoverableButton tileButton = hexagon.get(q,r).getButton();

        if (state.isLegal(q,r,state.getPlayerTurn()))
            tileButton.setColor(TileColor.LEGAL.get());
        else
            if (playerColor == 1)
                tileButton.setColor(TileColor.PLAYER1.get());
            else if (playerColor == 2)
                tileButton.setColor(TileColor.PLAYER2.get());
            else if (playerColor == 0)
                tileButton.setColor(TileColor.NONE.get());
    }


}
