import java.util.LinkedList;
import java.util.List;

public class Board {

    private int numberOfGroupsBlue;
    private int numberOfGroupsRed;

    private Tile[][][] board;
    private int boardLength;

    public Board(int boardSize) throws Exception {
        this.numberOfGroupsBlue = 0;
        this.numberOfGroupsRed = 0;

        if(boardSize%2==0) { throw new Exception("illegal boardSize"); }
        boardLength = boardSize; // can't be even
        int halfBoardLength = boardLength/2 +1;

        //  make and fill in the 3d-array with tiles according to this structure:
        // https://www.redblobgames.com/grids/hexagons/
        // But bigger and +4 to all coördinates because negative indexes can't be used in arrays
        // Start with the top row, and than go down till the end

        //first half of board
        this.board = new Tile[boardLength][boardLength][boardLength];
        for(int j=0; j<halfBoardLength; j++) {
            for(int i=(boardLength-halfBoardLength)-j; i<boardLength; i++) {
                board[i][boardLength-i][j] = new Tile();
            }
        }
        //Second half of board
        for(int j=halfBoardLength; j<boardLength; j++) {
            for(int i=j-(boardLength-halfBoardLength); i<boardLength; i++) {
                board[i][boardLength-i][j] = new Tile();
            }
        }

        //Update what the neighbours are
        //have to check 6 places, for each direction,
        // check where x stays the same, y+1, z+1, and check where y-1, z+1
        // and than for every direction

        //For every tile
        this.board = new Tile[boardLength][boardLength][boardLength];
        for(int j=0; j<halfBoardLength; j++) {
            for(int i=(boardLength-halfBoardLength)-j; i<boardLength; i++) {
                Tile tempTile = board[i][boardLength-i][j];
                addNeighboursOfTile(tempTile, i, boardLength-i, j);
            }
        }
        for(int j=halfBoardLength; j<boardLength; j++) {
            for(int i=j-(boardLength-halfBoardLength); i<boardLength; i++) {
                Tile tempTile = board[i][boardLength-i][j];
                addNeighboursOfTile(tempTile, i, boardLength-i, j);
            }
        }
    }

    //Update what the neighbours are
    //have to check 6 places, 2 for each direction,
    // check where x stays the same, y+1, z+1, and check where y-1, z+1
    // and then for every direction
    public void addNeighboursOfTile(Tile tile, int x, int y, int z) {
        // TODO: simplify it
        if(y!=boardLength && z!=0) {
            tile.addNeighbour(board[x][y + 1][z - 1]);
        }
        if(y!=0 && z!=boardLength) {
            tile.addNeighbour(board[x][y - 1][z + 1]);
        }
        if(x!=boardLength && z!=0) {
            tile.addNeighbour(board[x + 1][y][z - 1]);
        }
        if(x!=0 && z!=boardLength) {
            tile.addNeighbour(board[x - 1][y][z + 1]);
        }
        if(x!=0 && y!=boardLength) {
            tile.addNeighbour(board[x - 1][y + 1][z]);
        }
        if(x!=boardLength && y!=0) {
            tile.addNeighbour(board[x + 1][y - 1][z]);
        }
    }

    public int getBoardSize() {
        return boardLength;
    }


    public boolean[][][] validMovesBlue() {
        return new boolean[boardLength][boardLength][boardLength];
    }
    public boolean[][][] validMovesRed() { return new boolean[boardLength][boardLength][boardLength]; }

    public void move(int x, int y, int z, int c) throws Exception {
        int numberOfGroups;
        if(c==1) {
            numberOfGroups = numberOfGroupsRed;
        }
        else if(c==2) {
            numberOfGroups = numberOfGroupsBlue;
        }
        else {
            throw new Exception("illegal Colour");
        }

        Tile tile = board[x][y][z];
        if (tile.getColour()!=0)
        {
            throw new Exception("illegal move : already coloured");
        }

        //make list of all groups connected to tile
        List<Tile> tilesFromDifferentGroups = tile.getSurroundedTilesFromDifferentGroups(c);
        List<Tile> tilesConnectedAndColoured = tile.getSurroundedTilesFromGroups(c);


        //add a new group if no groups connected to that tile
        if(tilesFromDifferentGroups.size()==0) {
            if(c==1) {
                numberOfGroupsRed++;
            }
            else if(c==2) {
                numberOfGroupsBlue++;
            }
        }

        //need code to update a group if the new tile connects to it and check if legal
        int groupSizesFromAllNeighbours = 0;
        for(int i=0; i< tilesFromDifferentGroups.size(); i++) {
            groupSizesFromAllNeighbours += tilesFromDifferentGroups.get(i).getGroupSize();
        }
        if(groupSizesFromAllNeighbours+1 > numberOfGroups + 1 - (tilesFromDifferentGroups.size()))
        {
            throw new Exception("illegal move: groupsize too big");
        }
        else {
            tile.setColour(c);
        }

        //Update the number of groups
        if(c==1) {
            numberOfGroupsRed = numberOfGroupsRed += 1 - (tilesFromDifferentGroups.size());
        }
        else if(c==2) {
            numberOfGroupsBlue = numberOfGroupsBlue += 1 - (tilesFromDifferentGroups.size());
        }

        //TODO: update the connected tiles
        //do this in tile

    }


}
