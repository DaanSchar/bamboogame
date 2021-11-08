package com.maastricht.university.frontend;


import com.maastricht.university.logic.game.components.Hexagon;
import com.maastricht.university.logic.game.util.interfaces.IGameState;
import javafx.scene.layout.Pane;

public class TileMap {

    private int dimension = 9;
    private Hexagon<Tile> hexagon;

    private int mapX;
    private int mapY;
    private int TileSize;
    private Pane tileMapPane;

    public TileMap(int dimension, int mapX, int mapY, int TileSize) {
        this.dimension = dimension;
        this.mapX = mapX;
        this.mapY = mapY;
        this.TileSize = TileSize;

        hexagon = new Hexagon<>(dimension/2);

        for (int i = 0; i < hexagon.size(); i++)
            for (int j = 0; j < hexagon.size(); j++)
                hexagon.insert(i, j,
                        new Tile(i, j,
                                mapX + (i * TileSize) + (i + j * TileSize / 2),
                                mapY + j * TileSize, 30)
                );
    }

    public Pane getTileMapPane() {
        tileMapPane = new Pane();

        for(int i = 0; i<dimension;i++) {
            for(int j = 0; j<dimension;j++) {
                if(hexagon.get(i,j) != null)
                    tileMapPane.getChildren().add(hexagon.get(i,j).getButton());
            }
        }
        return tileMapPane;
    }

    /**
     * gives all tiles that are considered legal
     * for the current player a certain color
     */
    public void showLegalMoves() {
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                if (hexagon.get(i,j) != null)
                    determineTileColor(i, j);
    }

    public void updateTileColors() {
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                if (hexagon.get(i,j) != null)
                    determineTileColor(i, j);
    }

    private void determineTileColor(int q,int r) {
        IGameState state = Factory.getGameState();
        int playerColor = state.getPlayerColorOfTile(q,r);

        if (state.isLegal(q,r,state.getPlayerTurn()))
            setTileColor(q, r, TileColor.LEGAL.get());
        else
            if (playerColor == 1)
                setTileColor(q, r, TileColor.PLAYER1.get());
            else if (playerColor == 2)
                setTileColor(q, r,TileColor.PLAYER2.get() );
            else if (playerColor == 0)
                setTileColor(q, r, TileColor.NONE.get());
    }

    private void setTileColor(int q, int r, String color) {
        hexagon.get(q,r).getButton().setColor(color);
    }

}