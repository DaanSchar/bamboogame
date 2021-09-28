package com.maastricht.university.frontend;


import com.maastricht.university.logic.util.game.Hexagon;
import com.maastricht.university.logic.util.game.LogicTile;
import javafx.scene.layout.Pane;

public class TileMap {

    private int dimension = 9;
    private int mapX;
    private int mapY;
    private int TileSize; //Everything is scaled by TileSize, 30 works well rn
    Pane tileMapPane;

    Hexagon<Tile> hexagon;


    //Constructor generates a 2d array as shown in
    //https://www.redblobgames.com/grids/hexagons/#map-storage
    //access via x,y; Index is pretty meaningless rn
    /**
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
     */


    public TileMap(int dimension, int mapX, int mapY, int TileSize) {
        this.dimension = dimension;
        this.mapX = mapX;
        this.mapY = mapY;
        this.TileSize = TileSize;

        hexagon = new Hexagon<>(dimension/2);

        for (int i = 0; i < hexagon.size(); i++)
            for (int j = 0; j < hexagon.size(); j++)
                hexagon.insert(i, j, new Tile(mapX + (i * TileSize) + (i + j * TileSize / 2),
                        mapY + j * TileSize,
                        30,
                        j + i * dimension));


    }

    //Returns a Pane with the buttons
    public Pane getTileMapPane() {
        tileMapPane = new Pane(); //getTileMapPane

        for(int i = 0; i<dimension;i++) {
            for(int j = 0; j<dimension;j++) {
                if(hexagon.get(i,j) != null)
                    tileMapPane.getChildren().add(hexagon.get(i,j).getButton());
            }
        }
        return tileMapPane;
    }

    public Hexagon<Tile> getTileMap() {
        return hexagon;
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
        return hexagon.get(x,y);
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public void setMapX(int mapX) {
        this.mapX = mapX;
    }

    public void setMapY(int mapY) {
        this.mapY = mapY;
    }

    public int getTileSize() {
        return TileSize;
    }






}