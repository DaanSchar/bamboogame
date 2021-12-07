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

        Platform.runLater(() -> {
            GameScreen.updateCurrentPlayerLabel(state);
            GameScreen.updatePlayerLabels(state);
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
