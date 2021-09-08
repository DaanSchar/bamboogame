public class GameState {

    private boolean redsTurn;
    private Board board;

    public GameState(int boardSize) throws Exception {
        this.redsTurn = true;
        this.board = new Board(boardSize);

    }

    public boolean[][][] getLegalMovesBlue() {
        return new boolean[9][9][9];
    }

    public boolean[][][] getLegalMovesRed() {
        return new boolean[9][9][9];
    }

    public void moveBlue() {
        if(!redsTurn) {
            //update board
            //recount number of groups for Blue
            //update legal moves
        }
    }

    public void moveRed() {
        if(redsTurn) {
            //update board
            //recount number of groups for Red
            //update legal moves
        }
    }
}
