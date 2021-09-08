import java.util.LinkedList;
import java.util.List;

public class Board {

    private Tile[][][] board;

    public Board() {
        //make and fill in the 3d-array with tiles according to this structure:
        // https://www.redblobgames.com/grids/hexagons/
        // Start with the top row, and than go down till the end
        /*
        made a 7 tile board by accident
        this.board = new Tile[7][7][7];
        for(int i=3; i<7; i++) {
            board[i][6-i][0] = new Tile();
        }
        for(int i=2; i<7; i++) {
            board[i][6-i][1] = new Tile();
        }
        for(int i=1; i<7; i++) {
            board[i][6-i][2] = new Tile();
        }
        for(int i=0; i<7; i++) {
            board[i][6-i][3] = new Tile();
        }
        for(int i=1; i<7; i++) {
            board[i][6-i][4] = new Tile();
        }
        for(int i=2; i<7; i++) {
            board[i][6-i][5] = new Tile();
        }
        for(int i=3; i<7; i++) {
            board[i][6-i][6] = new Tile();
        }
        */
        /*
        //Row by row
        this.board = new Tile[9][9][9];
        for(int i=4; i<9; i++) {
            board[i][9-i][0] = new Tile();
        }
        for(int i=3; i<9; i++) {
            board[i][9-i][1] = new Tile();
        }
        for(int i=2; i<9; i++) {
            board[i][9-i][2] = new Tile();
        }
        for(int i=1; i<9; i++) {
            board[i][9-i][3] = new Tile();
        }
        for(int i=0; i<9; i++) {
            board[i][9-i][4] = new Tile();
        }

        for(int i=1; i<9; i++) {
            board[i][9-i][5] = new Tile();
        }
        for(int i=2; i<9; i++) {
            board[i][9-i][6] = new Tile();
        }
        for(int i=3; i<9; i++) {
            board[i][9-i][7] = new Tile();
        }
        for(int i=4; i<9; i++) {
            board[i][9-i][8] = new Tile();
        }
         */

        //first half of board
        this.board = new Tile[9][9][9];
        for(int j=0; j<5; j++) {
            for(int i=4-j; i<9; i++) {
                board[i][9-i][j] = new Tile();
            }
        }
        //Second half of board
        for(int j=5; j<9; j++) {
            for(int i=j-4; i<9; i++) {
                board[i][9-i][j] = new Tile();
            }
        }
    }
}
