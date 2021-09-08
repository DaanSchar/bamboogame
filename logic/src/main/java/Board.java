import java.util.LinkedList;
import java.util.List;

public class Board {

    private Tile[][][] board;

    public Board() {
        /*
        List<Tile> tiles = new LinkedList<Tile>();
        for(int i=0; i<27; i++) {
            tiles.add(new Tile());
        }
        */

        //make and fill in the 3d-array with tiles according to this structure:
        // https://www.redblobgames.com/grids/hexagons/
        // Start with the top row, and than go down till the end
        this.board = new Tile[7][7][7];
        for(int i=3; i<7; i++) {
            board[i][3-i][0] = new Tile();
        }
        for(int i=0; i<5; i++) {
            board[i][3-i][1] = new Tile();
        }


    }
}
