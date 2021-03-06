package com.maastricht.university.frontend;

import com.maastricht.university.frontend.components.HoverableButton;
import com.maastricht.university.frontend.components.tile.Tile;
import com.maastricht.university.frontend.components.tile.TileColor;
import com.maastricht.university.frontend.components.tile.TileMap;
import com.maastricht.university.frontend.scenes.GameScreen;
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

        for (Tile tile : tileMap.getHexagon())
            determineTileColor(
                    tile.getQ(),
                    tile.getR(),
                    tileMap.getHexagon(),
                    state
            );
    }

    private static void determineTileColor(int q, int r, Hexagon<Tile> hexagon, IGameState state) {
        int playerColor = state.getPlayerColorOfTile(q,r);
        HoverableButton tileButton = hexagon.get(q,r).getButton();

        if (state.isLegal(q,r,state.getPlayerTurn()))
            tileButton.setColor(TileColor.LEGAL);
        else
            if (playerColor == 1)
                tileButton.setColor(TileColor.PLAYER1);
            else if (playerColor == 2)
                tileButton.setColor(TileColor.PLAYER2);
            else if (playerColor == 0)
                tileButton.setColor(TileColor.NONE);
    }


}
