package com.maastricht.university.frontend.components.tile;


import com.maastricht.university.logic.game.components.Hexagon;
import javafx.scene.layout.Pane;

public class TileMap {

    private final int dimension;
    private final Hexagon<Tile> hexagon;

    public TileMap(int dimension, int mapX, int mapY, int TileSize) {
        this.dimension = dimension;
        hexagon = new Hexagon<>(dimension/2);

        for (int i = 0; i < hexagon.size(); i++)
            for (int j = 0; j < hexagon.size(); j++)
                hexagon.insert(i, j,
                        new Tile(i, j,
                                mapX + (i * TileSize) + (i + j * TileSize / 2f),
                                mapY + j * TileSize, 30)
                );
    }

    public Pane getTileMapPane() {
        Pane tileMapPane = new Pane();

        for (Tile tile : hexagon)
            tileMapPane.getChildren().add(tile.getButton());

        return tileMapPane;
    }

    public int getDimension() {
        return this.dimension;
    }

    public Hexagon<Tile> getHexagon() {
        return this.hexagon;
    }

}