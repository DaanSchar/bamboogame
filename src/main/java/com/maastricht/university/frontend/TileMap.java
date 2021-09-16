package com.maastricht.university.frontend;


import javafx.scene.layout.Pane;

public class TileMap {

    private Tile[][] tileMap;
    private int dimension = 9;
    private int mapX;
    private int mapY;
    private int TileSize; //Everything is scaled by TileSize, 30 works well rn
    Pane tileMapPane;


    //Constructor generates a 2d array as shown in
    //https://www.redblobgames.com/grids/hexagons/#map-storage
    //access via x,y; Index is pretty meaningless rn
    public TileMap(int mapX, int mapY, int TileSize) {
        this.mapX = mapX;
        this.mapY = mapY;
        this.TileSize = TileSize;
        tileMap = new Tile[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (i + j < 4 || i + j > 12)
                    tileMap[i][j] = null;
                else
                    tileMap[i][j] = new Tile(mapX + (i * TileSize) + (i + j * TileSize / 2),
                            mapY + j * TileSize,
                            30,
                            j + i * dimension);
            }
        }
    }

    //Returns a Pane with the buttons
    public Pane getTileMapPane() {
        tileMapPane = new Pane(); //getTileMapPane

        for(int i = 0; i<dimension;i++) {
            for(int j = 0; j<dimension;j++) {
                if(tileMap[i][j] != null)
                    tileMapPane.getChildren().add(tileMap[i][j].getButton());
            }
        }
        return tileMapPane;
    }

    public Tile[][] getTileMap() {
        return tileMap;
    }

    public int getMapX() {
        return mapX;
    }

    public int getMapY() {
        return mapY;
    }

    //return the Tile at the specified x and y
    //NOTE: this implementation is different from the logic's java/tile which uses 3d coordinates
    //IF NECESSARY CREATE TRANSLATION BETWEEN 2D AND 3D COORDS
    //https://www.redblobgames.com/grids/hexagons/#conversions
    public Tile getTile(int x, int y) {
        return tileMap[x][y];
    }

    public int getDimension() {
        return dimension;
    }
}