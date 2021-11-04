package com.maastricht.university.frontend;


import com.maastricht.university.logic.game.components.Hexagon;
import com.maastricht.university.logic.game.util.interfaces.IGameState;
import javafx.application.Platform;
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

    public int getDimension() {
        return this.dimension;
    }

    public Hexagon<Tile> getHexagon() {
        return this.hexagon;
    }

}