public class GameState {

    private int numberOfGroupsBlue;
    private int numberOfGroupsRed;
    private boolean redsTurn;
    private Tile[][][] board;

    public GameState() {
        this.numberOfGroupsBlue = 0;
        this.numberOfGroupsRed = 0;
        this.redsTurn = true;
        this.board = board;
    }

    public void getLegalMovesBlue() {
    }

    public void getLegalMovesRed() {
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

    public int getNumberOfGroupsBlue() {
        return numberOfGroupsBlue;
    }

    public int getNumberOfGroupsRed() {
        return numberOfGroupsRed;
    }
}
