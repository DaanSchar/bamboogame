package com.university.maastricht.tile;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class TileMap {

    private Tile[][] tileMap;
    private int dimension = 9;


    //Constructor generates a 2d array as shown in
    //https://www.redblobgames.com/grids/hexagons/#map-storage
    //acces via x,y; Index is pretty meaningless rn
    public TileMap() {
        tileMap = new Tile[dimension][dimension];

        for(int i = 0; i < dimension; i++) {
            for(int j = 0; j<dimension;j++) {
                if(i+j < 4 || i+j > 12)
                    tileMap[i][j] = null;
                else
                    tileMap[i][j] = new Tile(i,j,30,j+i*dimension);
            }
        }
    }

    public Tile[][] getTileMap() {
        return tileMap;
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


}
