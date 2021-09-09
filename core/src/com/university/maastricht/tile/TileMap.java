package com.university.maastricht.tile;


import com.badlogic.gdx.scenes.scene2d.Stage;

public class TileMap {

    private Tile[][] tileMap;
    private int dimension = 9;
    private int mapX;
    private int mapY;
    private int TileSize; //Everything is scaled by TileSize, 60 works well rn


    //Constructor generates a 2d array as shown in
    //https://www.redblobgames.com/grids/hexagons/#map-storage
    //acces via x,y; Index is pretty meaningless rn
    public TileMap(int mapX, int mapY, int TileSize) {
        this.mapX = mapX;
        this.mapY = mapY;
        this.TileSize = TileSize;
        tileMap = new Tile[dimension][dimension];

        for(int i = 0; i < dimension; i++) {
            for(int j = 0; j<dimension;j++) {
                if(i+j < 4 || i+j > 12)
                    tileMap[i][j] = null;
                else
                    tileMap[i][j] = new Tile(mapX + (i * TileSize) + (i+j * TileSize/2),mapY + j * TileSize,30,j+i*dimension);
            }
        }
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
    //TODO: IF NECESSARY CREATE TRANSLATION BETWEEN 2D AND 3D COORDS
    //https://www.redblobgames.com/grids/hexagons/#conversions
    public Tile getTile(int x, int y) {
        return tileMap[x][y];
    }

    public void addToStage(Stage stage) {
        for (Tile[] tileRow : tileMap) {
            for (Tile tile : tileRow) {
                if (tile != null) stage.addActor(tile.getActor());
            }
        }
    }

    //Moves tileMap to new Location
    public void moveTileMap(int newX, int newY) {
        for(int i = 0; i<dimension;i++) {
            for (int j = 0; j < dimension; j++) {
                if(tileMap[i][j] != null) {
                    tileMap[i][j].setX(newX + (i * TileSize) + (i+j * TileSize/2));
                    tileMap[i][j].setY(newY + j * TileSize);
                }
            }
        }
    }


}